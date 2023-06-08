package com.example.changyoong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.changyoong.databinding.ActivityNaviBinding


private const val TAG_EXERCISE = "exercise_fragment"
private const val TAG_PlAN = "plan_fragment"
private const val TAG_RECORD = "record_fragment"
private const val TAG_COMMUNITY = "community_fragment"
private const val TAG_MYPAGE = "mypage_fragment"


class NaviActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(TAG_MYPAGE, MyPageFragment())


        binding.bottomNavi.setOnItemSelectedListener { item->
            when(item.itemId) {
                R.id.exercisefragment -> setFragment(TAG_EXERCISE, ExerciseFragment())
                R.id.planfragment-> setFragment(TAG_PlAN, PlanFragment())
                R.id.recordfragment-> setFragment(TAG_RECORD, RecordFragment())
                R.id.communityfragment-> setFragment(TAG_COMMUNITY, CommunityFragment())
                R.id.mypagefragment-> setFragment(TAG_MYPAGE, MyPageFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val exercise = manager.findFragmentByTag(TAG_EXERCISE)
        val plan = manager.findFragmentByTag(TAG_PlAN)
        val record = manager.findFragmentByTag(TAG_RECORD)
        val community = manager.findFragmentByTag(TAG_COMMUNITY)
        val myPage = manager.findFragmentByTag(TAG_MYPAGE)

        if (exercise != null){
            fragTransaction.hide(exercise)
        }

        if (plan != null){
            fragTransaction.hide(plan)
        }

        if (record != null){
            fragTransaction.hide(record)
        }

        if (community != null){
            fragTransaction.hide(community)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (tag == TAG_EXERCISE) {
            if (exercise!=null){
                fragTransaction.show(exercise)
            }
        }


        else if (tag == TAG_PlAN) {
            if (plan!=null){
                fragTransaction.show(plan)
            }
        }
        else if (tag == TAG_RECORD) {
            if (record != null) {
                fragTransaction.show(record)
            }
        }

        else if (tag == TAG_COMMUNITY) {
            if (community != null) {
                fragTransaction.show(community)
            }
        }

        else if (tag == TAG_MYPAGE){
            if (myPage != null){
                fragTransaction.show(myPage)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}