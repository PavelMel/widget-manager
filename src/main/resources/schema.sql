
create table if not exists widget(
    id identity primary key,
    x integer not null,
    y integer not null,
    z integer not null,
    width integer not null,
    height integer not null,
    last_modification_date timestamp not null
);