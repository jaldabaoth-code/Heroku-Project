<h1>Heroku Project (Workshop, WCS CDA Java)</h1>

### Create a site web (social network) for superheroes by using Java Spring


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
* Install the <a href="https://github.com/RaphaelBS-WCS/Cerebook">Heroku CLI</a>

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
   2. Run `mvn package`
   3. Import the last version of the dataVx.sql file in your database
   4. Run the server with : `heroku local` OR `heroku local:start` OR `heroku local -p 8080`
   5. Go to <b>localhost:8080</b> with your favorite browser

   #### Deploying to Heroku
   1. Go to <a href="https://dashboard.heroku.com/apps">Heroku Dashboard</a>
   2. In heroku dashboard create new application : <b>New</b> -> <b>Create new app</b>
   3. Once you choose the name of the application and your region., click on the button to validate it
   4. In your terminal run : `heroku login`
   5. Git repository (Choose the cas 1, 2 or 3)
      1. If you don't have a git repository (Create a new Git repository)
         1. In your terminal run : `git init`
         2. In your terminal run : `heroku git:remote -a application_name`
         3. In your terminal run : `git add .`
         4. In your terminal run : `git commit -am "commit message"`
         5. In your terminal run : `git push heroku main` OR `git push heroku master` (main or master, depends on your branch)

      2. If you have a git repository
         1. In your terminal run : `heroku git:remote -a application_name`
         2. In your terminal run : `git push heroku main` OR `git push heroku master` (main or master, depends on your branch)

      3. If you want to clone the repository
         1. In your terminal run : `heroku git:clone -a application_name`
         2. In your terminal run : `cd application_name`
         3. Make some changes to the code you just cloned
         4. In your terminal run : `git add .`
         5. In your terminal run : `git commit -am "commit message"`
         6. In your terminal run : `git push heroku master` OR `git push heroku master` (main or master, depends on your branch)

   6.  Connect on your postgreSQL
      1. In your terminal run : `sudo su - postgres`
      2. Enter your password
      3. In heroku dashboard click on your project
      4. Go to Resources and click to <b>Heroku Postgres</b>
      5. A new page will open, click to Settings 
      6. unfold <b>Database Credentials</b>, you will get your database informationss
      7. In your terminal run : `psql --host=heroku_host_name  --port=5432 --username=heroku_username --password --dbname=heroku_database_name`
      8. Enter heroku database password

   7. Import the last version of the dataVx.sql file in the database
   8. Go to <b>https://application_name.herokuapp.com/</b> with your favorite browser

4. For login (locally or online) You can use :

    Username = deadpool<br/>
    Password = 123456789<br/>
    <b>OR</b><br/>
    Username = domino<br/>
    Password = 123456789<br/>

---

## The Links

<a https://github.com/Aenori/HerokuProject">Link to the repository of workshop of <b>WCS CDA Heroku Project</b></a>
<a https://github.com/heroku/java-getting-started">Link to the repository of <b>Heroku - Java Getting Started</b></a>
