package com.yunuscagliyan.soccerapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yunuscagliyan.soccerapp.data.model.Team
import com.yunuscagliyan.soccerapp.databinding.ItemTeamCardBinding

class TeamAdapter : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {


    private val teamList=ArrayList<Team?>()

    fun submitTeamList(list:List<Team?>){
        teamList.clear()
        teamList.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding =
            ItemTeamCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teamList[position]
        team?.let {
            holder.bindTeam(it)
        }
    }
    override fun getItemCount(): Int {
        return teamList.size
    }

    class TeamViewHolder(private val binding: ItemTeamCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTeam(team: Team) {
            binding.apply {
                tvTeamName.text = team.name ?: ""
                tvTeamShortName.text = team.shortCode ?: ""
                Glide.with(itemView)
                    .load(team.logo)
                    .into(ivTeamLogo)

            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Team?>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team) =
            oldItem.teamId == newItem.teamId

        override fun areContentsTheSame(
            oldItem: Team,
            newItem: Team
        ) = oldItem == newItem

    }




}