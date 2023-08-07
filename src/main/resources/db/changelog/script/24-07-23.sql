create or replace function trigger_lemma_up() returns trigger as
'
    begin
        new.frequency = old.frequency + 1;
        return new;
    end;
'LANGUAGE plpgsql;

create trigger lemma_up_before
    before update
    on lemma
    for each row
execute procedure trigger_lemma_up();

create or replace function trigger_index_up() returns trigger as
'
    begin
        new.rank = old.rank + new.rank;
        return new;
    end;
'LANGUAGE plpgsql;

create trigger lemma_up_before
    before update
    on index
    for each row
execute procedure trigger_index_up();