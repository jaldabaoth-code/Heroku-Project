<h1>Heroku Project (Heroku Workshop, WCS CDA Java)</h1>

### Deploying to Heroku, a project using Java Spring


---

### Prerequisites

* PostgreSQL (<a href="https://phoenixnap.com/kb/how-to-install-postgresql-on-ubuntu">Link for how to install <b>PostgreSQL</b></a>)
  #### Install PostgreSQL from PostgreSQL Apt Repository
1. `sudo apt-get install wget ca-certificates`
2. `wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -`
3. `sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'`
4. `sudo apt-get update`
5. `sudo apt-get install postgresql postgresql-contrib`
   #### Install PostgreSQL from Local Ubuntu Repository
6. `apt show postgresql`
7. `sudo apt install postgresql postgresql-contrib`
   #### Connect to PostgreSQL
8. `sudo su - postgres`
9. `psql`
   #### Check Connection Information
10. `\conninfo`
* Make sure you have <a href="https://www.oracle.com/java/technologies/downloads/">Java</a> and <a href="https://maven.apache.org/install.html">Maven</a> installed
* Install the <a href="https://devcenter.heroku.com/articles/heroku-cli">Heroku CLI</a>

### Other postgreSQL commands
1. Create new user : `create user user_name with encrypted password 'mypassword';`
2. Grant privileges : `grant all privileges on database database_name to user_name;`
3. Connect to new user : `psql -d database_name -U user_name -h localhost`
4. Delete a user : `sudo su - postgres` -> `psql` -> `DROP OWNED BY user_name;` -> `DROP ROLE user_name;`
5. Connect to database : `\c database_name`
6. List all Users in PostgreSQL : `\du`
7. List all Databases in PostgreSQL : `\l`
8. List all Tables in PostgreSQL : `\dt`

### Steps

1. Clone the repo from GitHub : `git clone git@github.com:jaldabaoth-code/Heroku-Project.git`
2. Enter the directory : `cd Heroku-Project`
3. Choose <b>Running Locally</b> or <b>Deploying to Heroku</b>

   #### Running Locally
    1. Create the database
    2. Create <b>.env</b> file from <b>.env.model</b> and add your Database parameters
    3. In your terminal run : `mvn package`
    4. Run the server with : `heroku local` or `heroku local:start` or `heroku local -p 8080`
    5. Import the last version of the <b>dataVx.sql</b> file in your database
    6. Go to <b>http://localhost:8080</b> with your favorite browser
    7. For login, you can use :

       Username = deadpool<br/>
       Password = 123456789<br/>
       <b>OR</b><br/>
       Username = domino<br/>
       Password = 123456789<br/>

   #### Deploying to Heroku
    1. Go to <a href="https://dashboard.heroku.com/apps">Heroku Dashboard</a>
    2. Create a new application : <b>New</b> -> <b>Create new app</b>
    3. Once you choose the name of the application and your region, click on the button <b>Create app</b> to validate it
    4. In your terminal run : `heroku login`
    5. Login to the browser page that has just opened
    6. Git repository (Choose the cas a, b or c)
        1. If you don't have a git repository (Create a new Git repository)
            1. In your terminal run : `git init`
            2. In your terminal run : `heroku git:remote -a application_name`
            3. In your terminal run : `git add .`
            4. In your terminal run : `git commit -am "commit message"`
            5. In your terminal run : `git push heroku main` or `git push heroku master` (main or master, it depends on what is your branch)

        2. If you have a git repository
            1. In your terminal run : `heroku git:remote -a application_name`
            2. In your terminal run : `git push heroku main` or `git push heroku master` (main or master, it depends on what is your branch)

        3. If you want to clone the repository
            1. In your terminal run : `heroku git:clone -a application_name`
            2. In your terminal run : `cd application_name`
            3. Make some changes to the code you just cloned
            4. In your terminal run : `git add .`
            5. In your terminal run : `git commit -am "commit message"`
            6. In your terminal run : `git push heroku master` or `git push heroku master` (main or master, it depends on what is your branch)

    7.  Connect on your postgreSQL
        1. In <a href="https://dashboard.heroku.com/apps">Heroku Dashboard</a> click on your project
        2. Go to <b>Resources</b> and click to <b>Heroku Postgres</b>
        3. A new page will open, click to <b>Settings</b>
        4. Unfold <b>Database Credentials</b> : Here you will get your database information
        5. In your terminal run : `sudo su - postgres`
        6. Enter your password
        7. Run : `psql --host=heroku_host_name  --port=5432 --username=heroku_username --password --dbname=heroku_database_name` (replaces <b>heroku_host_name</b>, <b>5432</b>, <b>heroku_username</b> and <b>heroku_database_name</b> by database information you obtained at <b>step d</b>)
        8. Enter heroku database password you obtained at <b>step d</b>

    8. Import the last version of the <b>dataVx.sql</b> file in your database
    9. Go to <b>https://application_name.herokuapp.com</b> with your favorite browser
    10. For login, you can use :

        Username = deadpool<br/>
        Password = 123456789<br/>
        <b>OR</b><br/>
        Username = domino<br/>
        Password = 123456789<br/>

---

## The Links

<a href="https://github.com/Aenori/HerokuProject">Link to the repository of workshop of <b>WCS CDA Heroku Project</b></a><br/>
<a href="https://github.com/heroku/java-getting-started">Link to the repository of <b>Heroku - Java Getting Started</b></a>
