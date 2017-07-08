package br.ufs.demos.rxmvp.playground.trivia.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufs.demos.rxmvp.playground.R;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.ComposedWithSpannedStyles;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.FactViewModel;
import br.ufs.demos.rxmvp.playground.trivia.presentation.models.NumberAndFact;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bira on 7/4/17.
 */

public class FactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FactViewModel> models = new ArrayList<>();

    private LayoutInflater inflater;

    public FactsAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addModel(FactViewModel modelToBeAdded) {
        int actualPosition = models.size();
        models.add(modelToBeAdded);
        notifyItemInserted(actualPosition);
    }

    public void clear() {
        models.clear();
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        return models.get(position).viewType();
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case FactViewModel.TYPE_TWO_LABELS:
                return asTwoLabelsHolder(parent);

            case FactViewModel.TYPE_SINGLE_LABEL:
                return asSingleLabelHolder(parent);
        }

        throw new IllegalArgumentException("Invalid viewType");
    }

    private RecyclerView.ViewHolder asTwoLabelsHolder(ViewGroup parent) {
        View card = inflater.inflate(R.layout.listitem_fact_and_number, parent, false);
        return new TwoLabelsHolder(card);
    }

    private RecyclerView.ViewHolder asSingleLabelHolder(ViewGroup parent) {
        View card = inflater.inflate(R.layout.listitem_fact_withstyles, parent, false);
        return new SingleLabelHolder(card);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        FactViewModel viewModel = models.get(position);

        switch (viewModel.viewType()) {

            case FactViewModel.TYPE_TWO_LABELS:
                bindAsTwoLabels(holder, viewModel);
                break;

            case FactViewModel.TYPE_SINGLE_LABEL:
                bindAsSingleLabel(holder, viewModel);
                break;
        }

    }

    private void bindAsSingleLabel(RecyclerView.ViewHolder holder, FactViewModel viewModel) {
        ComposedWithSpannedStyles composed = (ComposedWithSpannedStyles) viewModel;
        SingleLabelHolder vh = (SingleLabelHolder) holder;
        vh.singleLabel.setText(composed.formattedFact());
    }

    private void bindAsTwoLabels(RecyclerView.ViewHolder holder, FactViewModel viewModel) {
        NumberAndFact numberAndFact = (NumberAndFact) viewModel;
        TwoLabelsHolder vh = (TwoLabelsHolder) holder;
        vh.labelNumber.setText(numberAndFact.relatedNumber());
        vh.labelFact.setText(numberAndFact.formattedFact());
    }

    @Override public int getItemCount() {
        return models.size();
    }

    static class TwoLabelsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.label_number) TextView labelNumber;
        @BindView(R.id.label_fact) TextView labelFact;

        public TwoLabelsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class SingleLabelHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.label_single_message) TextView singleLabel;

        public SingleLabelHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
