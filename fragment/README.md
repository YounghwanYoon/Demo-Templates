# Template/Demontration of Fragment with FragmentStatePagerAdapter

FragmentStatePagerAdapter
-This version of the pager is more useful when there are a large number of pages,
 working more like a list view. When pages are not visible to the user,
 their entire fragment may be destroyed, only keeping the saved state of that fragment.
 This allows the pager to hold on to much less memory associated with each visited page as compared to
 FragmentPagerAdapter at the cost of potentially more overhead when switching between pages.

Steps:

Gradle
1. Add design library to gradle which version match with the compact
*Note (Bottom Layout which provided by android studio does not helping to switching around with fragments)

Layout
1. Create menu layout to design item/tabs for Bottom Navigation
2. Create container/activity_view of fragments and add menu layout under <design.widget.BottomNavigationView>
3. Recommended way of placing views is by using RelativeLayout and apply "layout_alignParentBottom = true" under <BottomNavigationView>
4.  Create Fragment Layout

Fragment
1.Create Fragment by extend Fragment
2. override OnCreateView by returning view using inflater.inflate(R.layout.fragment_home, container, false)
3. Do the same for rest of fragments

FragmentPagerAdapter
1. Create an instance called FragmentList:MuableList<Fragment>
2. Create an add Fragment Method with parameter with a fragment which add to the instance
3. Override two method getItem & getCount
4. For getItem(), return currently selected fragment by returning mFragmentList.get(position)
5. For getCount(), return total size of fragment to display by returning mFragmentList.size

Activity (container Activity)
1. Inside OnCreate() -> instantiate three variables SectionPageAdapter, ViewPager, BottomNavigationView
2. For Bottom Navigation, after instantiation, setOnNavigationItemSelectedListener
which handles fragment change upon selecting navigation item
3. For SectionPageAdapter, instantiate with getSupportFragmentManager() and add Fragments
4. For ViewPager, instantiate with finViewById and add OnPageChangeListener
by override onPageSelected which handle bottomNav Item update
whenever user change fragment using swipe instead of bottom_navigation item

Source:
https://developer.android.com/reference/android/support/v4/app/FragmentStatePagerAdapter


Testing

