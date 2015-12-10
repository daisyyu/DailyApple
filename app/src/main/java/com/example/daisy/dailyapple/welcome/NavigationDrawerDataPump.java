package com.example.daisy.dailyapple.welcome;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.example.daisy.dailyapple.DAO.WordsListHolder;
import com.example.daisy.dailyapple.learn.LearningActivityFragment;

import java.util.*;

/**
 * Created by Daisy on 11/30/15.
 */
public class NavigationDrawerDataPump {
    private static List<GroupItem> listDataHeader;
    private static HashMap<ParentTitle, List<ChildItem>> listChildData;
    private FragmentManager fragmentManager;

    public NavigationDrawerDataPump(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public List<GroupItem> getListDataHeader() {
        if (listDataHeader == null) {
            listDataHeader = new ArrayList<>();
            listDataHeader.add(new GroupItem(ParentTitle.HOME, false,
                    fragmentManager));
            listDataHeader.add(new GroupItem(ParentTitle.LEARNING, true,
                    fragmentManager));
            listDataHeader.add(new GroupItem(ParentTitle.REVIEW, true,
                    fragmentManager));
            listDataHeader.add(new GroupItem(ParentTitle.TEST, true,
                    fragmentManager));
            listDataHeader.add(new GroupItem(ParentTitle.SETTING, false,
                    fragmentManager));
        }
        return listDataHeader;
    }

    public HashMap<ParentTitle, List<ChildItem>> getListChildData() {
        if (listChildData == null) {
            listChildData = new LinkedHashMap<>();
            // learn
            List<ChildItem> learnList = new ArrayList<>();
            // reivew
            List<ChildItem> reviewList = new ArrayList<>();
            // test
            List<ChildItem> testList = new ArrayList<>();
            for (WordsListHolder.ListName listName : WordsListHolder.ListName.values()) {
                if (listName.isHidden()) {
                    continue;
                }
                learnList.add(new ChildItem(listName.getListName(), listName,
                        fragmentManager, ParentTitle.LEARNING));
                reviewList.add(new ChildItem(listName.getListName(), listName,
                        fragmentManager, ParentTitle.REVIEW));
                testList.add(new ChildItem(listName.getListName(), listName,
                        fragmentManager, ParentTitle.TEST));
            }
            // learning list doesn't need custom list
            learnList.remove(learnList.size() - 1);
            // put into HashMap
            listChildData.put(ParentTitle.LEARNING, learnList);
            listChildData.put(ParentTitle.REVIEW, reviewList);
            listChildData.put(ParentTitle.TEST, testList);
        }
        return listChildData;
    }

    static enum ParentTitle {
        HOME("home"), LEARNING("learning"), REVIEW("review"), TEST("test"),
        SETTING("settings");

        String title;

        ParentTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    abstract class DrawerItem {
        String title = "";
        FragmentManager fragmentManager;

        public DrawerItem(String title, FragmentManager manager) {
            this.title = title;
            this.fragmentManager = manager;
        }

        abstract public Fragment getFragment();
    }

    class GroupItem extends DrawerItem {
        boolean isExpandable;
        ParentTitle parentTitle;

        public GroupItem(ParentTitle title, boolean isExpandable, FragmentManager manager) {
            super(title.getTitle(), manager);
            this.parentTitle = title;
            this.isExpandable = isExpandable;
        }

        public Fragment getFragment() {
            Fragment fragment = new Fragment();
            if (title == "Home") {
                fragment = new WelcomeFragment();
            }
            // TODO for settings
            return fragment;
        }
    }

    class ChildItem extends DrawerItem {
        WordsListHolder.ListName listName;
        ParentTitle parentTitle;

        public ChildItem(String title, WordsListHolder.ListName listName,
                         FragmentManager manager, ParentTitle parentTitle) {
            super(title, manager);
            this.listName = listName;
            this.parentTitle = parentTitle;
        }

        public Fragment getFragment() {
            Fragment fragment = new Fragment();
            switch (parentTitle) {

                case HOME:
                    break;
                case LEARNING:
                    //TODO: need to refactor LearningActivity to Learning
                    // Fragment to contain view pager
//                    fragment = new LearningActivityFragment();
                    break;
                case REVIEW:
                    break;
                case TEST:
                    break;
                case SETTING:
                    break;
            }
            switch (listName) {

                case TESTING_LIST:
                    break;
//                case DAILY_LIST:
//                    break;
//                case GRE_LIST:
//                    break;
//                case CUSTOM_LIST:
//                    break;
            }
            return null;
        }
    }
}
