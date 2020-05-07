package com.github.azdrachak.otusandroid

import java.util.*

class Data {
    companion object {
        val favouritesList: MutableList<MovieItem> = mutableListOf()

        private const val ocean11desc: String =
            """
        После выхода из тюрьмы вора Дэнни Оушена не проходит и 24 часов, а он уже планирует организовать самое сложное ограбление казино в истории. Он хочет украсть 160 млн американских долларов из трёх самых преуспевающих казино Лас-Вегаса.
        Все эти казино принадлежат элегантному и в то же время жестокому дельцу Терри Бенедикту, который только и мечтает о том, как встретится с бывшей женой Дэнни Оушена — Тесс.\n\n
        Всего за одну ночь Дэнни подбирает команду из одиннадцати «специалистов», способных совершить эту дерзкую кражу. 
        В команде Оушена оказываются первоклассный карточный шулер, молодой искусный вор-карманник и гениальный разрушитель.
        Чтобы совершить этот безумный и сложный грабёж, Оушену придётся рисковать своей жизнью.
            """

        private const val ocean12desc =
            """
        Гангстер Денни Оушен собрал свою банду профессиональных грабителей и мошенников, и теперь там появились новые лица. Они задумали совершить три ограбления в европейских столицах.
        Владелец казино Терри Бенедикт, которого Оушен ограбил в Лас-Вегасе, жаждет мести и пытается настигнуть банду. За ней также охотятся агенты Европола и Диннер Джекет.
            """

        val items = mutableListOf(
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 11",
                ocean11desc,
                R.drawable.ocean11,
                isFavorite = false,
                isVisited = false
            ),
            MovieItem(
                UUID.randomUUID().toString(),
                "Ocean's 12",
                ocean12desc,
                R.drawable.ocean12,
                isFavorite = false,
                isVisited = false
            )
        )
    }
}