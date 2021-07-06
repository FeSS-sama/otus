with source as
    (
        select  date
                , sum(earnings) as total
        from {{ ref('sat_days_details') }}
        join {{ ref('hub_days') }} using (hub_days_pk)
        group by date
    )

select * from source
order by total desc