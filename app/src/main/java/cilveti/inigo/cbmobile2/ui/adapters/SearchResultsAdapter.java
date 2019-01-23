package cilveti.inigo.cbmobile2.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cilveti.inigo.cbmobile2.R;
import cilveti.inigo.cbmobile2.business.interfaces.MainProcess;
import cilveti.inigo.cbmobile2.models.SearchResult;

/**
 * this adapter has a query of its own, with a listener that does quite a bit of work {@link SearchResultsAdapter(com.couchbase.lite.LiveQuery)}
 * so it has to be stopped every
 */
public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SimpleViewHolder> {

    protected Context context;
    private AdapterView.OnItemClickListener onItemClickListener;
    private List<SearchResult> results;
    private MainProcess mainProcess;

    public SearchResultsAdapter(List<SearchResult> results, Context context, MainProcess mainProcess) {
        this.context = context;
        this.results = results;
        this.mainProcess = mainProcess;
    }

    /**
     * recibe el enumerator con el que actualizar las vistas
     * y las actualiza
     * 
     */
    public void updateValues(List<SearchResult> newSearchResults){
        
        this.results = newSearchResults;

        ((Activity) SearchResultsAdapter.this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    /**
     * Inflate a new instance of the ViewHolder. The Recycler View handles reuses already
     * instantiated ViewHolders when possible.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false);
        return new SimpleViewHolder(view);
    }

    /**
     * Use the position to get the corresponding query row and populate the ViewHolder that
     * was created above.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {

        final SearchResult result = (SearchResult) getItem(position);
        if(result!=null && result.getName()!=null){

            try{
                holder.tv_value.setText(wordFirstCap((String) result.getName()));
                List<String> list = (List<String>) result.getDescription();
                holder.tv_description.setText(list.get(0));
            }catch (Exception e){
                e.printStackTrace();
            }

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainProcess.openConjuro(result.getId());
                }
            });

        }
    }

    public String wordFirstCap(String str)
    {
        String[] words = str.trim().split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < words.length; i++)
        {
            if(words[i].trim().length() > 0)
            {
                ret.append(Character.toUpperCase(words[i].trim().charAt(0)));
                ret.append(words[i].trim().substring(1));
                if(i < words.length - 1) {
                    ret.append(' ');
                }
            }
        }

        return ret.toString();
    }
    
    @Override
    public int getItemCount() {

        return results != null ? results.size() : 0;
    }


    public Object getItem(int i) {

        return results != null ? results.get(i) : null;
    }


    private static final int COLOR_RED = 1;
    private static final int COLOR_GREEN = 2;
    private static final int COLOR_NONE = 0;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_value;
        public RelativeLayout relative;
        public LinearLayout linearLayout;
        public TextView tv_description;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            tv_value = (TextView) itemView.findViewById(R.id.tv_name);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.parent);

        }
    }
    
}


