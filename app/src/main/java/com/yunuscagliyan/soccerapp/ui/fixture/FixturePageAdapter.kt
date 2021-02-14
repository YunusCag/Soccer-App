package com.yunuscagliyan.soccerapp.ui.fixture

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunuscagliyan.soccerapp.R
import com.yunuscagliyan.soccerapp.data.model.Fixture
import com.yunuscagliyan.soccerapp.databinding.ItemFixturePageCardBinding
import timber.log.Timber

class FixturePageAdapter : RecyclerView.Adapter<FixturePageAdapter.FixtureViewHolder>() {

    private val fixtureList = ArrayList<Fixture?>()

    fun submitFixtureList(list: List<Fixture?>) {
        fixtureList.clear()
        fixtureList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        val binding =
            ItemFixturePageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FixtureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        val fixture = fixtureList[position]
        fixture?.let {
            holder.bindFixture(it)
        }
    }

    override fun getItemCount(): Int {
        return fixtureList.size
    }

    class FixtureViewHolder(private val binding: ItemFixturePageCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindFixture(fixture: Fixture) {
            binding.apply {
                val weekText=itemView.resources.getString(R.string.week_with_dot)
                tvWeekIndex.text = "${fixture.weekIndex}"+weekText
                val matcheAdapter = MatcheAdapter()
                fixture.matches?.let { matches ->
                    matcheAdapter.submitMatcheList(matches)
                }
                rvMatches.apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(
                        itemView.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = matcheAdapter
                }
            }
        }
    }


}