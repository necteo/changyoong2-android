package com.example.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.presentation.R
import com.example.presentation.ui.community.CommunityFragment
import com.example.presentation.ui.exerciselist.ExerciseFragment
import com.example.presentation.ui.exerciselist.ExerciseListFragment
import com.example.presentation.ui.plan.PlanFragment
import com.example.presentation.ui.profile.ProfileFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NaviActivity : AppCompatActivity() {

    private lateinit var exerciseFragment: ExerciseFragment
    private lateinit var planFragment: PlanFragment
    private lateinit var recordFragment: ExerciseListFragment
    private lateinit var communityFragment: CommunityFragment
    private lateinit var profileFragment: ProfileFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      setContentView(R.layout.activity_navi)

        exerciseFragment = ExerciseFragment()
        planFragment = PlanFragment()
        recordFragment = ExerciseListFragment()
        communityFragment = CommunityFragment()
        profileFragment = ProfileFragment()

        supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, exerciseFragment).commit()

        val bottomNavigationView = findViewById<NavigationBarView>(R.id.bottomNavi)
        bottomNavigationView.setOnItemSelectedListener (
            object: NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    var selectedFragment: Fragment? = null
                    Log.d("nav select", item.toString())
                    when(item.toString()) {
                        "운동검색" -> selectedFragment = exerciseFragment
                        "운동계획" -> selectedFragment = planFragment
                        "운동기록" -> selectedFragment = recordFragment
                        "커뮤니티" -> selectedFragment = communityFragment
                        "마이페이지" -> selectedFragment = profileFragment
                    }
                    selectedFragment?.let {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.mainFrameLayout, selectedFragment)
                            .commit()
                        return true
                    }
                    return false
                }
            }
        )
    }
}