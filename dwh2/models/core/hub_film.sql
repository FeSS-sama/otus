with source as
    (
        select  hash(film_name) as hub_film_pk
                , film_name
                , getdate() as load_date
                , 'stg_movies' as load_source
        from (select distinct film_name from {{ ref('stg_movies') }}) s1
    )

select * from source