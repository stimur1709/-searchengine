create or replace function trigger_page_in_up() returns trigger as
'
    begin
        update site
        set status_time = now()
        where id = new.site_id;
        return new;
    end;
'LANGUAGE plpgsql;

create trigger balance_site_in_up_before
    after insert or update
    on page
    for each row
execute procedure trigger_page_in_up();

insert into site(status, last_error, url, name)
VALUES ('FAILED', 'Индексация не была запущена', 'https://lenta.ru', 'Лента.ру'),
       ('FAILED', 'Индексация не была запущена', 'https://skillbox.ru', 'Skillbox'),
       ('FAILED', 'Индексация не была запущена', 'https://playback.ru', 'PlayBack.Ru');