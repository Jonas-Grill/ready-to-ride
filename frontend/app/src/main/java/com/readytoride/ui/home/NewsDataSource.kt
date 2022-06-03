package com.readytoride.ui.home

import com.readytoride.R

class NewsDataSource() {

    fun loadNewsFeed(): List<NewsEntry> {
        return listOf<NewsEntry>(
            NewsEntry(
                R.string.id1,
                R.string.newsTitle1,
                R.string.newsDesc1,
                R.drawable.trainer1
            ),
            NewsEntry(
                R.string.id2,
                R.string.newsTitle2,
                R.string.newsDesc2,
                R.drawable.trainer2
            ),
            NewsEntry(
                R.string.id3,
                R.string.newsTitle3,
                R.string.newsDesc3,
                R.drawable.trainer3
            ),
            NewsEntry(
                R.string.id3,
                R.string.newsTitle3,
                R.string.newsDesc3,
                R.drawable.trainer3
            ),
            NewsEntry(
                R.string.id3,
                R.string.newsTitle3,
                R.string.newsDesc3,
                R.drawable.trainer3
            ),
            NewsEntry(
                R.string.id3,
                R.string.newsTitle3,
                R.string.newsDesc3,
                R.drawable.trainer3
            )
        )
    }
}