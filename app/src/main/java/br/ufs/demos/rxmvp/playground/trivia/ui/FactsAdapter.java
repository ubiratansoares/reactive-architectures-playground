package br.ufs.demos.rxmvp.playground.trivia.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.trivia.presentation.FactViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bira on 7/4/17.
 */

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.FactHolder> {

    private List<FactViewModel> models = new ArrayList<>();

    public void addModel(FactViewModel model) {
        int actualPosition = models.size();
        models.add(model);
        notifyItemInserted(actualPosition);
    }

    @Override public FactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context host = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(host);
        View card = inflater.inflate(R.layout.listitem_fact_about_number, parent, false);
        return new FactHolder(card);
    }

    @Override public void onBindViewHolder(FactHolder holder, int position) {
        FactViewModel factViewModel = models.get(position);
        holder.labelNumber.setText(factViewModel.number);
        holder.labelFact.setText(factViewModel.fact);
    }

    @Override public int getItemCount() {
        return models.size();
    }

    static class FactHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.label_number) TextView labelNumber;
        @BindView(R.id.label_fact) TextView labelFact;

        public FactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
