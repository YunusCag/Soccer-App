package com.yunuscagliyan.soccerapp.ui.fixture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunuscagliyan.soccerapp.data.model.Fixture
import com.yunuscagliyan.soccerapp.databinding.ItemMatchesBinding
import java.text.SimpleDateFormat
import java.util.*

class MatcheAdapter : RecyclerView.Adapter<MatcheAdapter.MatcheViewHolder>() {
    private val matcheList = ArrayList<Fixture.Matche?>()

    fun submitMatcheList(list: List<Fixture.Matche?>) {
        matcheList.clear()
        matcheList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatcheViewHolder {
        val binding = ItemMatchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatcheViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatcheViewHolder, position: Int) {
        val matche = matcheList[position]
        matche?.let {
            holder.bindMatche(it)
        }
    }

    override fun getItemCount(): Int {
        return matcheList.size
    }

    class MatcheViewHolder(private val binding: ItemMatchesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val inputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private val outputFormatter = SimpleDateFormat("dd MMM yyyy HH:mm:ss")
        fun bindMatche(matche: Fixture.Matche) {
            binding.apply {
                val homeTeam = matche.homeTeam
                matche.matchStart?.let { dateString ->
                    //val date = inputFormatter.format(dateString)
                    tvDate.text = dateString
                }

                homeTeam?.let { home ->
                    tvHomeTeamName.text = home.name
                    Glide.with(itemView.context)
                        .load(home.logo)
                        .into(ivHomeTeam)

                }

                val otherTeam = matche.awayTeam
                otherTeam?.let { other ->
                    tvOtherTeamName.text = other.name ?: ""
                    Glide.with(itemView.context)
                        .load(other.logo)
                        .into(ivOtherTeam)
                }

                matche.stats?.let { stats ->
                    if (matche.status != "finished") {
                        tvScore.text = "-:-"
                    } else {
                        tvScore.text = "${stats.homeScore ?: 0}:${stats.etScore ?: 0}"

                    }
                }
            }
        }
    }


}