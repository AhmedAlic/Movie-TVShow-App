package  com.myapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> MovieFragment()
            1 -> TVShowFragment()
            else -> TVShowFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "MOVIES"
            1 -> return "TV SHOWS"
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return 2
    }
}