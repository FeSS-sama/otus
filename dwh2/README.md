# Проект витрин с использованием dbt

## Задание
Спроектировать схему данных + Построить витрину Использовать Vertica (Docker) или BigQuery \
• Датасет: Захват данных из divolte (или GCP Public Datasets) \
Definition of Done: \
• DDL объектов \
• DML шагов преобразований \
• Опционально: Тестирование на наличие ошибок в данных

## Решение 
1. Склонировать этот проект
2. Распаковать **~/data/data.7z**
2. Развернуть Vertica в Docker
   ```bash
   docker run --name vertica -p 5433:5433 -d  -v /c/data:/home/dbadmin/docker jbfavre/vertica:latest
   ```
3. Переложить **profiles.yml** в папку **~/.dbt**
4. Установить dbt - https://docs.getdbt.com/dbt-cli/installation
5. Перейти в корневую папку проекта и поочерёдно выполнить

```bash
dbt seed            # Первоначальная загрузка csv-файлов
dbt run             # Построение слоёв и витрин
dbt docs generate   # Сгенерировать документацию
dbt docs serve      # Посмотреть документацию. Если порт занят - добавить параметр --port [port]
```