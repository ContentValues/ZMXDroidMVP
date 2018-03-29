package com.mwee.xdroid.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mwee.xdroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroidbase.router.Router;

/**
 * Created by zhangmin on 2017/9/20.
 */

public class ExpandableActivity extends XActivity {
    @BindView(R.id.exlist_lol)
    ExpandableListView exlist_lol;

    //头数据源
    List<ExpandableItem> groupLists = new ArrayList<>();

    //子数据源
    List<List<ExpandableItem>> childLists = new ArrayList<>();

    @Override
    public void initData(Bundle savedInstanceState) {

        /*头部数据源*/
        ExpandableItem group1 = new ExpandableItem("AD");
        ExpandableItem group2 = new ExpandableItem("AP");
        ExpandableItem group3 = new ExpandableItem("Tank");
        groupLists.add(group1);
        groupLists.add(group2);
        groupLists.add(group3);



        /*子数据源*/
        List<ExpandableItem> temp = new ArrayList<>();
        ExpandableItem child1_goupAD = new ExpandableItem("后裔");
        ExpandableItem child2_goupAD = new ExpandableItem("鲁班七号");
        ExpandableItem child3_goupAD = new ExpandableItem("狄仁杰");
        temp.add(child1_goupAD);
        temp.add(child2_goupAD);
        temp.add(child3_goupAD);
        childLists.add(temp);

        temp.clear();
        ExpandableItem child1_goupAP = new ExpandableItem("不知火舞");
        ExpandableItem child2_goupAP = new ExpandableItem("小乔");
        ExpandableItem child3_goupAP = new ExpandableItem("诸葛亮");
        temp.add(child1_goupAP);
        temp.add(child2_goupAP);
        temp.add(child3_goupAP);
        childLists.add(temp);

        temp.clear();
        ExpandableItem child1_goupTank = new ExpandableItem("程咬金");
        ExpandableItem child2_goupTank = new ExpandableItem("亚瑟");
        ExpandableItem child3_goupTank = new ExpandableItem("猴子");
        ExpandableItem child4_goupTank = new ExpandableItem("张飞");
        temp.add(child1_goupTank);
        temp.add(child2_goupTank);
        temp.add(child3_goupTank);
        temp.add(child4_goupTank);
        childLists.add(temp);

        final ExpandableAdapter expandableAdapter = new ExpandableAdapter(getBaseContext(), groupLists, childLists);
        exlist_lol.setAdapter(expandableAdapter);
        exlist_lol.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //ExpandableAdapter adapter = (ExpandableAdapter) parent.getAdapter();
                ExpandableItem expandableItem = expandableAdapter.getChild(groupPosition, childPosition);
                Toast.makeText(getBaseContext(), "name-->" + expandableItem.name + "  groupPosition-->" + groupPosition + "  childPosition-->" + childPosition, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {
            exlist_lol.expandGroup(i);
        }
        exlist_lol.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                ExpandableItem expandableItem = expandableAdapter.getGroup(groupPosition);
                Toast.makeText(getBaseContext(), "name-->" + expandableItem.name + "  groupPosition-->" + groupPosition , Toast.LENGTH_SHORT).show();
                return true;
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_expandable;
    }


    class ExpandableAdapter extends BaseExpandableListAdapter {

        Context mContext;

        //头数据源
        List<ExpandableItem> groupLists = new ArrayList<>();

        //子数据源
        List<List<ExpandableItem>> childLists = new ArrayList<>();

        public ExpandableAdapter(Context mContext, List<ExpandableItem> groupLists, List<List<ExpandableItem>> childLists) {
            this.mContext = mContext;
            this.groupLists = groupLists;
            this.childLists = childLists;

        }


        @Override
        public int getGroupCount() {
            return groupLists.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childLists.get(groupPosition).size();
        }

        @Override
        public ExpandableItem getGroup(int groupPosition) {
            return groupLists.get(groupPosition);
        }

        @Override
        public ExpandableItem getChild(int groupPosition, int childPosition) {
            return childLists.get(groupPosition).get(childPosition);
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
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_exlist_group, parent, false);
                groupViewHolder = new GroupViewHolder(convertView);
                convertView.setTag(groupViewHolder);
            } else {
                groupViewHolder = (GroupViewHolder) convertView.getTag();
            }
            ExpandableItem groupItem = groupLists.get(groupPosition);
            groupViewHolder.groupText.setText(groupItem.name);

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_exlist_item, parent, false);
                childViewHolder = new ChildViewHolder(convertView);
                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }

            ExpandableItem childItem = childLists.get(groupPosition).get(childPosition);
            childViewHolder.childText.setText(childItem.name);

            return convertView;
        }

        //设置子列表是否可选中
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }


        class GroupViewHolder {

            TextView groupText;

            public GroupViewHolder(View convertView) {
                groupText = (TextView) convertView.findViewById(R.id.tv_group_name);
            }
        }

        class ChildViewHolder {

            TextView childText;

            public ChildViewHolder(View convertView) {
                childText = (TextView) convertView.findViewById(R.id.tv_name);
            }
        }

    }


    class ExpandableItem {

        String name = "";

        public ExpandableItem(String name) {
            this.name = name;
        }

    }

    public static void launch(Activity activity) {
        Router.newIntent(activity)
                .to(ExpandableActivity.class)
                .launch();
    }


}
