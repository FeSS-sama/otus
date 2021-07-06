with source as
    (
        select  hash(film_name, date) as hub_days_pk
                , film_name
                , date
                , getdate() as load_date
                , 'stg_daily_data' as load_source
        from {{ ref('stg_daily_data') }}
    )

select * from source