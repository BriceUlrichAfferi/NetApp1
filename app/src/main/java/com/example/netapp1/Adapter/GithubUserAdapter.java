package com.example.netapp1.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.example.netapp1.Models.GithubUser;
import com.example.netapp1.databinding.FragmentMainItemBinding;
import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder> {

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    private final Listener callback;
    private List<GithubUser> githubUsers;
    private RequestManager glide;

    public GithubUserAdapter(List<GithubUser> githubUsers, RequestManager glide, Listener callback) {
        this.githubUsers = githubUsers;
        this.glide = glide;
        this.callback = callback;
    }

    @Override
    public GithubUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GithubUserViewHolder(FragmentMainItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(GithubUserViewHolder viewHolder, int position) {
        viewHolder.updateWithGithubUser(this.githubUsers.get(position), this.glide, this.callback);
    }

    @Override
    public int getItemCount() {
        return this.githubUsers.size();
    }

    public GithubUser getUser(int position) {
        return this.githubUsers.get(position);
    }
}
