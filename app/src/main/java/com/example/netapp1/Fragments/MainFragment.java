package com.example.netapp1.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.netapp1.Adapter.GithubUserAdapter;
import com.example.netapp1.Models.GithubUser;
import com.example.netapp1.R;
import com.example.netapp1.Utils.GithubStreams;
import com.example.netapp1.Utils.ItemClickSupport;
import com.example.netapp1.databinding.FragmentMainBinding;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainFragment extends Fragment implements GithubUserAdapter.Listener {

    private FragmentMainBinding binding;
    private List<GithubUser> githubUsers;
    private GithubUserAdapter adapter;
    private Disposable disposable;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MainFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();

        swipeRefreshLayout = binding.swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(this::refreshData);

        this.configureOnClickRecyclerView();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(binding.fragmentMainRecyclerView, R.id.fragment_main_recycler_view)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        GithubUser user = adapter.getUser(position);
                        Toast.makeText(getContext(), "You clicked on user: " + user.getLogin(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClickDeleteButton(int position) {
        GithubUser user = adapter.getUser(position);
        Toast.makeText(getContext(), "You are trying to delete user: " + user.getLogin(), Toast.LENGTH_SHORT).show();
    }

    private void configureRecyclerView() {
        this.githubUsers = new ArrayList<>();
        this.adapter = new GithubUserAdapter(this.githubUsers, Glide.with(this), this);
        binding.fragmentMainRecyclerView.setAdapter(this.adapter);
        binding.fragmentMainRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void executeHttpRequestWithRetrofit() {
        this.disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(List<GithubUser> users) {
                updateUI(users);
            }

            @Override
            public void onError(Throwable e) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onComplete() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void refreshData() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        executeHttpRequestWithRetrofit();
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateUI(List<GithubUser> users) {
        githubUsers.addAll(users);
        adapter.notifyDataSetChanged();
    }
}
