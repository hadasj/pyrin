# pyrin
Projekt PIRIN BG-1

Setup
-----

To build the image
# cd pyrin/docker/postgres-primary
# docker build --rm -t reg-5.i.cz:5000/postgres .

Run
--------------------
# docker-compose up -d
# or
# docker run --name pyrin_primary_1 -d -p 5432:5432 reg-5.i.cz:5000/postgres
    
# passwd postgres123
# psql -h reg-5.i.cz -p 5432 -U postgres -W
