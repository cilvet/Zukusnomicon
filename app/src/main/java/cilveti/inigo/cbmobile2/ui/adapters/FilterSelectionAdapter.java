package cilveti.inigo.cbmobile2.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.models.Option;
import cilveti.inigo.cbmobile2.ui.fragments.filters.IFiltersParentFragment;

public class FilterSelectionAdapter extends RecyclerView.Adapter<FilterSelectionAdapter.FilterViewHolder>{

    List<Option> items;
    Context context;
    IFiltersParentFragment parentFragment;

    public FilterSelectionAdapter(IFiltersParentFragment parentFragment, Context context){
        this.items = parentFragment.getOptions();
        this.context = context;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        final Option result = items.get(position);
        if(result!=null && result.getValue()!=null){

            holder.textView.setText(result.getValue());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parentFragment.onItemClicked(result.getCode());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        RelativeLayout relativeLayout;

        public FilterViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_option_name);
            relativeLayout = itemView.findViewById(R.id.rv_item_filter);
        }
    }

}
