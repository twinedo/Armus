package com.armusat_taubah.jadwal;

/**
 * Created by TWIN on 6/24/2016.
 */


import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.armusat_taubah.R;

public class JadwalNov extends Fragment {

    View rootView;
    ExpandableListView ExpOkt;
    private String[] groups;
    private String[][] child;

    public JadwalNov() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = getResources().getStringArray(R.array.ExpNov);
        child = new String[][]{getResources().getStringArray(R.array.Nov4),
                getResources().getStringArray(R.array.Nov11),
                getResources().getStringArray(R.array.Nov18),
                getResources().getStringArray(R.array.Nov25)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_jadwal_nov, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ExpOkt = (ExpandableListView) view.findViewById(R.id.lvExpNov);
        ExpOkt.setAdapter(new ExpandableListAdapter(groups, child));
        ExpOkt.setGroupIndicator(null);
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private final LayoutInflater inf;
        private String[] groups;
        private String[][] child;

        public ExpandableListAdapter(String[] groups, String[][] child) {
            this.groups = groups;
            this.child = child;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return child[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            viewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.nov_list_group, parent, false);

                holder = new viewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.listTitle);
                convertView.setTag(holder);
            } else {
                holder = (viewHolder) convertView.getTag();
            }

            holder.text.setText(getGroup(groupPosition).toString());
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            viewHolder holder;
            if (convertView == null) {
                convertView = inf.inflate(R.layout.nov_list_item, parent, false);
                holder = new viewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.expandedListItem);
                convertView.setTag(holder);
            } else {
                holder = (viewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());
            return convertView;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class viewHolder {
            TextView text;
        }

    }
}



