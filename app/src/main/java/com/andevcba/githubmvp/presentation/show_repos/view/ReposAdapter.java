package com.andevcba.githubmvp.presentation.show_repos.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.andevcba.githubmvp.R;
import com.andevcba.githubmvp.presentation.show_repos.model.RepoUI;
import com.andevcba.githubmvp.presentation.show_repos.model.StickyHeaderUI;
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.andevcba.githubmvp.presentation.show_repos.view.ViewType.EMPTY;
import static com.andevcba.githubmvp.presentation.show_repos.view.ViewType.REPO_ITEM;
import static com.andevcba.githubmvp.presentation.show_repos.view.ViewType.STICKY_HEADER;

/**
 * Adapter responsible of showing:
 * 1. An empty view if there is no data to show.
 * 2. A list of repos by a given username.
 *
 * @author lucas.nobile
 */
public class ReposAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderHandler {

    private Context context;
    private List<ViewType> items;
    private int emptyResId;
    private ShowReposFragment.OnItemSelectedListener itemSelectedListener;

    public ReposAdapter(List<ViewType> items, int emptyResId, ShowReposFragment.OnItemSelectedListener itemSelectedListener, Context context) {
        this.items = items;
        this.emptyResId = emptyResId;
        this.itemSelectedListener = itemSelectedListener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case EMPTY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
                holder = new EmptyViewHolder(view);
                break;
            case STICKY_HEADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sticky_header, parent, false);
                holder = new ViewHolder(view, itemSelectedListener);
                break;
            case REPO_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
                holder = new ViewHolder(view, itemSelectedListener);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (items.isEmpty()) {
            ((EmptyViewHolder) holder).tvEmtpy.setText(emptyResId);
        } else {
            ViewType item = items.get(position);
            ((ViewHolder) holder).tvName.setText(item.getName());
            if (REPO_ITEM == item.getType()) {
                setAnimation(holder.itemView);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.isEmpty()) {
            return ViewType.EMPTY;
        } else {
            return items.get(position).getType();
        }
    }

    @Override
    public int getItemCount() {
        if (items.isEmpty()) {
            return 1; // must return one otherwise none item is shown
        }
        return items.size();
    }

    @Override
    public List<ViewType> getAdapterData() {
        return items;
    }

    public void addAll(List<ViewType> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearAll() {
        items.clear();
        notifyDataSetChanged();
    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.right_in);
        viewToAnimate.startAnimation(animation);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvName;
        private CircleImageView imageView;
        private ShowReposFragment.OnItemSelectedListener itemSelectedListener;

        ViewHolder(View itemView, ShowReposFragment.OnItemSelectedListener listener) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            imageView = (CircleImageView) itemView.findViewById(R.id.civ_user);
            itemSelectedListener = listener;
            itemView.setOnClickListener(this);

            if (imageView != null ) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemSelectedListener.onImageSelected(imageView);
                    }
                });
            }
        }

        @Override
        public void onClick(View v) {
            // Use viewHolder#getAdapterPosition() because recyclerView#getAdapterPosition() returns -1 in some cases
            int position = this.getAdapterPosition();
            if (position == -1) {
                position = 0;
            }
            ViewType item = items.get(position);
            if (ViewType.STICKY_HEADER == item.getType()) {
                StickyHeaderUI stickyHeaderUI = (StickyHeaderUI) item;
                itemSelectedListener.onStickyHeaderSelected(stickyHeaderUI);
            } else {
                RepoUI repo = (RepoUI) item;
                itemSelectedListener.onRepoSelected(repo);
            }
        }
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEmtpy;

        EmptyViewHolder(View itemView) {
            super(itemView);
            tvEmtpy = (TextView) itemView.findViewById(R.id.tv_empty);
        }
    }
}
