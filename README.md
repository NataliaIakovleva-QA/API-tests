# Проект API-tests
## Содержание
> ➠ [Технологии и инструменты](#Технологии и инструменты)
>
> ➠ [Описание проекта](#Описание-проекта)
>
> ➠ [Список проверок](#список-проверок-реализованных-в-автотестах)
>
> ➠ [Пример ручного выполнения тестов с помощью Postman](#Пример-ручного-выполнения-тестов-с-помощью-Postman)
>
> ➠ [Структура проекта](#Структура-проекта)
>
> ➠ [Интеграция с Jenkins](#Интеграция с Jenkins)
>
> ➠ [Интеграция с Allure TestOps](#Интеграция с Allure TestOps)
>
> ➠ [Allure отчет](#Allure отчет)
>
> ➠ [Оповещения](#Уведомление в Telegram о результатах сборки Jenkins с помощью Telegram-бота)

## Используемые технологии
![This is an image](/design/icons/Java.png)![This is an image](/design/icons/Gradle.png)![This is an image](/design/icons/Rest-Assured.png)![This is an image](/design/icons/Intelij_IDEA.png)![This is an image](/design/icons/JUnit5.png)![This is an image](/design/icons/Jenkins.png)![This is an image](/design/icons/Allure_Report.png)![This is an image](/design/icons/AllureTestOps.png)![This is an image](/design/icons/Telegram.png)

- Язык для написания тестов : [Java](https://www.java.com/ru/)
- Фреймворк для модульного тестирования : [Junit5](https://github.com/junit-team/junit5)
- Система автоматической сборки : [Gradle](https://github.com/gradle)
- Удаленный запуск с выбором параметров для тестов реализован при помощи :  [Jenkins](https://www.jenkins.io/)
- Отчеты о пройденных тестах формируются при помощи : [Allure](https://github.com/allure-framework)
- Так же отчеты о тестировании отправляются в мессенджер при помощи <code>Telegram</code> бота
- Реализована интеграция  с [Allure TestOps](https://qameta.io/)
- Реализована интеграция с [Jira Software](https://www.atlassian.com/software/jira)
- Реализация API тестов при помощи <code>RestAssured</code>

## Описание проекта
Дипломный проект реализации автотестирования **Rest Api**.<br/>
>В качестве объекта тестирования выбран сайт https://petstore.swagger.io/ с открытым api.<br/>

![This is an image](/design/images/petstore.png)

Сайт позволяет получать информацию о питомцах, заказах и пользователях, а также создавать их.

#### Особенности реализации тестового проекта
- Модели получаемых данных описаны с помощью библиотеки Lombok.
- Использованы спецификации
- Использованы шаблоны форматирования логов запросов.
- Использованы кастомные шаблоны запросов и ответов

## Примеры автоматизированных тест-кейсов
- [x] Find by status Available. Поиск по статусу Available.
- [x] Find by status Pending. Поиск по статусу Pending.
- [x] Find by status Sold. Поиск по статусу Sold.
- [x] Find by status Null. Поиск по пустому статусу.
- [x] Create Pet. Создание питомца.
- [x] Create User. Создание пользователя.
- [x] Create User with array. Создание пользователя с массивом.
- [x] Create User with list. Создание пользователя со списком.
- [x] Get empty User. Получение пустого пользователя.
- [x] Get null User. Получение пользователя с null.
- [x] Create Order. Создание заказа.
- [x] Find Order. Поиск заказа.


## Пример ручного выполнения тестов с помощью Postman
![This is an image](/design/images/postman.png)

## Структура проекта
- [x] data - генерирование тестовых данных
- [x] helpers - подключение кастомных шаблонов для Allure Report
- [x] models - модели данных для тестов
- [x] spec - спецификации, необходимые для тестов
- [x] resources - кастомные шаблоны для Allure Report

![This is an image](/design/images/project.png)


## Интеграция с Jenkins
> <a target="_blank" href="https://jenkins.autotests.cloud/job/API-tests/">Ссылка на проект в Jenkins</a>

![This is an image](/design/images/jenkins_api.png)

### **Параметры сборки в Jenkins:**

- *task (выбор группы тестов для запуска, по умолчанию все - test)*

<a id="console"></a>
## Команды для запуска из терминала
___
***Локальный запуск:***
```bash  
gradle clean test
```

***Удалённый запуск через Jenkins:***
```bash  
clean ${TASK}
```

## Интеграция с Allure TestOps
> <a target="_blank" href="https://allure.autotests.cloud/project/4671/dashboards">Сссылка на проект в AllureTestOps</a> (запрос доступа admin@qa.guru)

### Итоговые dashboard по результатам сборок
![This is an image](/design/images/dashboard_overview.png)
### Пример автоматически сгенерированными тест-кейсами в Allure TestOps
![This is an image](/design/images/allure_testcases.png)
### Пример выполнения джоб в Allure TestOps
![This is an image](/design/images/launches.png)

<a id="allure"></a>
## <a name="Allure"></a>Allure [отчет](https://jenkins.autotests.cloud/job/API-tests/20/allure/)</a>

### Итоговые dashboard в Allure Report
![This is an image](/design/images/allure_report_dashboard.png)
### Список тест-кейсов в Allure Report
![This is an image](/design/images/testcases.png)
### Графики Dashboards в Allure Report
![This is an image](/design/images/graph.png)
### Графики Dashboards в Allure Report
![This is an image](/design/images/graph2.png)

## Уведомление в Telegram о результатах сборки Jenkins с помощью Telegram-бота
![This is an image](/design/images/bot.png)

