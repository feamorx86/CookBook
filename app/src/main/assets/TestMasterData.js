{
    "categories" : [
        {
            "id" : 0,
            "name" : "Быстрые блюда",
            "tags" : ["Быстро", "Мультиварка", "Гадость"]
        },
        {
            "id" : 1,
            "name" : "Каши",
            "tags" : ["Каша", "Мультиварка", "Гадость"]
        },
        {
            "id" : 2,
            "name" : "Для сковороды",
            "tags" : ["Долго", "Сковорода", "Вкусно", "Серёже"]
        }
    ],

    "ingredients" : [
        {
            "id"    : 0,
            "name"  : "Вода",
            "description" : "Лучше кипяченая",
            "tags" : ["Есть всегда"]
        },
        {
            "id"    : 1,
            "name"  : "Молоко",
            "description" : "2.5%",
            "tags" : ["Бстро портится", "Купить"]
        },
        {
            "id"    : 2,
            "name"  : "Яйца",
            "tags" : ["Есть всегда"]
        },
        {
            "id"    : 3,
            "name"  : "Мука"
        },
        {
            "id"    : 4,
            "name"  : "Сахар",
            "tags" : ["Есть всегда"]
        },
        {
            "id"    : 5,
            "name"  : "Яблоки",
            "tags" : ["Купить"]
        },
        {
            "id"    : 6,
            "name"  : "Манка"
        },
        {
            "id"    : 7,
            "name"  : "гашеная сода или разрыхлитель теста"
        },
        {
            "id"    : 8,
            "name"  : "соль",
            "tags" : ["Есть всегда"]
        },
        {
            "id"    : 9,
            "name"  : "Сливочное масло"
        },
        {
            "id"    : 10,
            "name"  : "Чай чёрный"
        }

    ],

    "recipes" : [
        {
            "id"            : 0,
            "name"          : "Чай",
            "time"          : "Мигом",
            "minutes"       : 5.5,

            "categories"    : [0],

            "ingredients"   : [
                {
                    "id"            : 0,
                    "ingredientId"  : 0,
                    "comment"       : "вода",
                    "count"         : 400,
                    "strCount"      : "мл"
                },
                {
                    "id"            : 1,
                    "ingredientId"  : 10,
                    "comment"       : "чай",
                    "count"         : 1,
                    "strCount"      : "шт"
                },
                {
                    "id"            : 2,
                    "ingredientId"  : 4,
                    "comment"       : "сахар",
                    "strCount"      : "1-3 ложки"
                }
            ],

            "descriptions"  : [
                "Взять чайник, вылить старую воду, ополоснуть под краном.",
                "Налить свежую воду на одного по 200-400 мл.",
                "Включить чайник",
                "Положить в кружку пакетик с чаем",
                "Дождаться пока чайник выключится",
                "Залить кипяток в кружку",
                "Положить и размешать сахар по вкусу",
                "Чтобы было вкуснее можно добавить лимон"
            ],

            "tags" : ["Есть всегда"]
        },
        {
            "id"            : 1,
            "name"          : "Чай 2",
            "time"          : "Мигом 2",

            "categories"    : [0, 1, 2],

            "ingredients"   : [
                {
                    "id"            : 0,
                    "ingredientId"  : 0,
                    "comment"       : "вода",
                    "count"         : 400,
                    "strCount"      : "мл2"
                },
                {
                    "id"            : 1,
                    "ingredientId"  : 10,
                    "comment"       : "чай",
                    "count"         : 1,
                    "strCount"      : "шт2"
                },
                {
                    "id"            : 2,
                    "ingredientId"  : 4,
                    "comment"       : "сахар",
                    "strCount"      : "1-3 ложки2"
                }
            ],

            "descriptions"  : [
                "2-Взять чайник, вылить старую воду, ополоснуть под краном.",
                "2-Налить свежую воду на одного по 200-400 мл.",
                "2-Включить чайник",
                "2-Положить в кружку пакетик с чаем",
                "2-Дождаться пока чайник выключится",
                "2-Залить кипяток в кружку",
                "2-Положить и размешать сахар по вкусу",
                "2-Чтобы было вкуснее можно добавить лимон"
            ],

            "tags" : ["2-Есть всегда"]
        }
    ],

    "updateTime" : "11-01-2017 23:35:00",
    "version" : "1"
}