package com.example.socialize20.adapters
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter


class viewPageAdapter(fm: androidx.fragment.app.FragmentManager):FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    //    we'll make two lists it will count that how much content is inside them
    val fragmentList = mutableListOf<Fragment>()
    //    this variable will give title
    val titleList = mutableListOf<String>()
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList.get(position)
    }
    fun addFragment (fragment: Fragment,title:String){
        fragmentList.add(fragment)
        titleList.add(title)
    }

}