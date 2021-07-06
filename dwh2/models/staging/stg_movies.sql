with source as
    (
    select * from {{ ref('Attributes_DataFrame') }}
    ),
renamed as
    (
    select
        "Title" as film_name,
        "Domestic" as domestic,
        "International" as international,
        "Budget" as budget,
        "Distributor" as distributor,
        "MPAA-Rating" as mpaa_rating,
        "Runtime" as runtime,
        "Genres" as genres
    from source
    )

select * from renamed
