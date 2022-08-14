package com.wildcodeschool.cerebook.controller;

import com.wildcodeschool.cerebook.entity.*;
import com.wildcodeschool.cerebook.repository.*;
import com.wildcodeschool.cerebook.service.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParticipationRepository participationRepository;

    @Autowired
    EventCategoryRepository eventCategoryRepository;

    @GetMapping("")
    public String showAllEvents(Principal principal, Model model) {
        if (principal == null) {
            return "redirect: /login";
        }
        List<Event> allEvents = eventRepository.findAllEvents();
        User user = userRepository.getUserByUsername(principal.getName());
        model.addAttribute("cerebookUser", user.getCerebookUser());
        model.addAttribute("allEvents", allEvents);
        model.addAttribute("principal", principal);
        return "events/allEvents";
    }

    @GetMapping("/myown")
    public String showMyEvents(@ModelAttribute Event event, Model model, Principal principal) {
        if (principal == null) {
            return "redirect: /login";
        }
        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);
        User user = userRepository.getUserByUsername(principal.getName());
        model.addAttribute("cerebookUser", user.getCerebookUser());
        try {
            model.addAttribute("eventsByUser", eventRepository.getAllEventsByCreator(currentUser));
        } catch (Exception e) {
            return "redirect:/events";
        }
        return "events/eventsByUser";
    }

    @GetMapping("/show/{eventId}")
    public String showEventsById(@ModelAttribute Event event, Model model, Principal principal, @PathVariable Long eventId, RedirectAttributes redirAttrs) {
        boolean isParticipating = false;
        boolean isOwner = false;
        Event currentEvent = eventRepository.getEventById(eventId);
        List<Participation> participants = participationRepository.getAllByEvent(currentEvent);
        String username = principal.getName();
        CerebookUser currentCerebookUser = userRepository.getUserByUsername(username).getCerebookUser();
        String eventCreator = currentEvent.getCreator().getUsername();
        model.addAttribute("eventById", eventRepository.getEventById(eventId));
        model.addAttribute("findAllParticipations", participants);
        User user = userRepository.getUserByUsername(principal.getName());
        model.addAttribute("cerebookUser", user.getCerebookUser());
        Set<CerebookUser> cuSet = participants.stream().map(Participation::getParticipant).collect(Collectors.toSet());
        if (cuSet.contains(currentCerebookUser)) {
            isParticipating = true;
        }
        model.addAttribute("isParticipating", isParticipating);
        if (Objects.equals(username, eventCreator)) {
            isOwner = true;
        }
        model.addAttribute("isOwner", isOwner);
        return "events/showEventById";
    }

    @PostMapping("/delete_event/{eventId}")
    public String removeEvent(@ModelAttribute Event event, @PathVariable Long eventId, Principal principal, Model model, RedirectAttributes redirAttrs) {
        Event currentEvent = eventRepository.getEventById(eventId);
        model.addAttribute("eventById", currentEvent);
        String username = principal.getName();
        String eventCreator = currentEvent.getCreator().getUsername();
        if (Objects.equals(username, eventCreator)) {
            redirAttrs.addFlashAttribute("error", "You are not authorized.");
        } else {
            eventRepository.delete(currentEvent);
            redirAttrs.addFlashAttribute("success", "Event successfully deleted.");
        }
        return "redirect:/events/myown";
    }

    @PostMapping("/inscription_event/{eventId}")
    public String enrollParticipantToEvent(@ModelAttribute Event event, Model model, Principal principal, Participation participation, @PathVariable Long eventId, RedirectAttributes redirAttrs) {
        Event currentEvent = eventRepository.getEventById(eventId);
        model.addAttribute("eventById", currentEvent);
        String username = principal.getName();
        CerebookUser currentCerebookUser = userRepository.getUserByUsername(username).getCerebookUser();
        List<Participation> participants = participationRepository.getAllByEvent(currentEvent);
        model.addAttribute("findAllParticipations", participants);
        // Recovering all the users from the all Participants of the current Event
        Set<CerebookUser> cuSet = participants.stream().map(Participation::getParticipant).collect(Collectors.toSet());
        if (cuSet.contains(currentCerebookUser)) {
            redirAttrs.addFlashAttribute("error", "You are already subscribed in this event.");
        } else {
            participation.setParticipant(currentCerebookUser);
            participation.setEvent(eventRepository.getEventById(eventId));
            participationRepository.save(participation);
            redirAttrs.addFlashAttribute("success", "You are now inscribed in this event.");
        }
        return "redirect:/events/show/{eventId}";
    }

    @PostMapping("/unroll_event/{eventId}")
    public String unenrollParticipantFromEvent(@ModelAttribute Event event, Model model, Principal principal, Participation participation, @PathVariable Long eventId, RedirectAttributes redirAttrs) {
        Event currentEvent = eventRepository.getEventById(eventId);
        model.addAttribute("eventById", currentEvent);
        String username = principal.getName();
        CerebookUser currentCerebookUser = userRepository.getUserByUsername(username).getCerebookUser();
        List<Participation> participants = participationRepository.getAllByEvent(currentEvent);
        Set<CerebookUser> cuSet = participants.stream().map(Participation::getParticipant).collect(Collectors.toSet());
        if (cuSet.contains(currentCerebookUser)) {
            Participation relativeParticipation = participationRepository.getParticipationByEventAndByCerebookUser(currentEvent, currentCerebookUser);
            participationRepository.delete(relativeParticipation);
            redirAttrs.addFlashAttribute("success", "You were unrolled from this event.");
        } else {
            redirAttrs.addFlashAttribute("error", "You are not enrolled in this event");
        }
        return "redirect:/events/show/{eventId}";
    }

    @GetMapping("/edit/{id}")
    public String updateEvent(@PathVariable("id") Long id, @ModelAttribute Event event, Model model, Principal principal) {
        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);
        model.addAttribute("cerebookUser", currentUser.getCerebookUser());
        model.addAttribute("eventByIdAndUser", eventRepository.getEventByIdAndByCreator(currentUser, id));
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());
        return "events/update";
    }


    @PostMapping("/update/{id}")
    public String modifyEvent(@PathVariable("id") Long id, @Valid @ModelAttribute Event event, Model model, Principal principal, @RequestParam("date") Date eventDate, BindingResult bindingResult, @RequestParam("backgroundPhotoFile") MultipartFile multipartFile) throws ServletException, IOException {
        Event currentEvent = eventRepository.getEventById(id);
        if (!principal.getName().equals(currentEvent.getCreator().getUsername())) {
            throw new IOException("User not allowed.");
        }
        String username = principal.getName();
        User currentUser = userRepository.getUserByUsername(username);
        long millis = System.currentTimeMillis();
        Date localDate = new Date(millis);
        model.addAttribute("eventByIdAndUser", eventRepository.getEventByIdAndByCreator(currentUser, id));
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());
        model.addAttribute("cerebookUser", currentUser.getCerebookUser());
        if (eventDate.before(localDate)) {
            bindingResult.rejectValue("date", "error.date", "The event date cannot be before the current date.");
        }
        if (multipartFile.getSize() > 1048576) {
            bindingResult.rejectValue(String.valueOf(multipartFile), "error.date", "The event date cannot be before the current date.");
            return "events/update";
        }
        if (multipartFile.isEmpty()) {
            return "events/create";
        }
        if (bindingResult.hasErrors()) {
            return "events/update";
        } else {
            event.setCreatedAt(currentEvent.getCreatedAt());
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            event.setBackgroundPhoto(fileName);
            event.setCreator(currentUser);
            event.setUpdatedAt(localDate);
            Event updatedEvent = eventRepository.save(event);
            String uploadDir = "src/main/resources/public/images/WebContent/events-uploaded-files/" + updatedEvent.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return "redirect:/events/show/" + id;
        }
    }

    @GetMapping("/create")
    public String createEvent(Model model, Principal principal) {
        model.addAttribute("event", new Event());
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());
        User user = userRepository.getUserByUsername(principal.getName());
        model.addAttribute("cerebookUser", user.getCerebookUser());
        return "events/create";
    }

    @PostMapping("/create")
    public String saveEvent(@Valid @ModelAttribute Event event, Model model, BindingResult bindingResult, Principal principal, @RequestParam("date") Date eventDate, @RequestParam("backgroundPhotoFile") MultipartFile multipartFile) throws ServletException, IOException {
        long millis = System.currentTimeMillis();
        Date localDate = new Date(millis);
        model.addAttribute("eventCategories", eventCategoryRepository.getAllEventCategories());
        if (eventDate.before(localDate)) {
            bindingResult.rejectValue("date", "error.date", "The event date cannot be before the current date.");
        }
        if (multipartFile.isEmpty()) {
            return "events/create";
        }
        if (bindingResult.hasErrors()) {
            return "events/create";
        } else {
            event.setCreatedAt(localDate);
            String username = principal.getName();
            User currentUser = userRepository.getUserByUsername(username);
            event.setCreator(currentUser);
            // Getting the name of the uploaded file and persisting it to the database
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            event.setBackgroundPhoto(fileName);
            Event savedEvent = eventRepository.save(event);
            String uploadDir = "src/main/resources/public/images/WebContent/events-uploaded-files/" + savedEvent.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return "redirect:/events";
        }
    }
}
