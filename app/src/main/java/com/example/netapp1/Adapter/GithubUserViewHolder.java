package com.example.netapp1.Adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.netapp1.Models.GithubUser;
import com.example.netapp1.databinding.FragmentMainItemBinding;
import java.lang.ref.WeakReference;

public class GithubUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private FragmentMainItemBinding binding;
    private WeakReference<GithubUserAdapter.Listener> callbackWeakRef;

    public GithubUserViewHolder(FragmentMainItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.fragmentMainItemDelete.setOnClickListener(this); // Assuming you have a delete button with this id
    }

    public void updateWithGithubUser(GithubUser githubUser, RequestManager glide, GithubUserAdapter.Listener callback) {
        this.binding.fragmentMainItemTitle.setText(githubUser.getLogin());

        // Update TextView & ImageView
        this.binding.fragmentMainItemWebsite.setText(githubUser.getHtmlUrl());
        glide.load(githubUser.getAvatarUrl()).apply(RequestOptions.circleCropTransform()).into(binding.fragmentMainItemImage);

        this.callbackWeakRef = new WeakReference<>(callback);
    }

    @Override
    public void onClick(View view) {
        GithubUserAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) {
            callback.onClickDeleteButton(getAdapterPosition());
        }
    }
}
