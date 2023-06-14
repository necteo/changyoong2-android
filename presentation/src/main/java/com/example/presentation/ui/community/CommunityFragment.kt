package com.example.presentation.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.R
import com.example.presentation.adapter.MyAdapter
import com.example.presentation.ui.exerciselist.ExerciseListUiModel
import com.example.presentation.ui.exerciselist.ExerciseViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class CommunityFragment : Fragment(R.layout.fragment_community) {
    private val viewModel: ExerciseViewModel by activityViewModels()

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter : MyAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArrayList : ArrayList<News>

    lateinit var imageId: Array<Int>
    private lateinit var heading: Array<String>
    private lateinit var news: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    companion object{
        fun newInstance(param1: String, param2: String) =
            CommunityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(newsArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize(){
        val exercise: List<ExerciseListUiModel> = viewModel.exerciseAllList.value

        newsArrayList = ArrayList()

        imageId = arrayOf(
            R.drawable.aryung,
            R.drawable.dumbell,
            R.drawable.calendar,
            R.drawable.login2,
            R.drawable.profile,
            R.drawable.aryung,
            R.drawable.dumbell,
            R.drawable.calendar,
            R.drawable.login2,
            R.drawable.profile
        )

        heading = arrayOf(
            getString(R.string.head_1),
            getString(R.string.head_2),
            getString(R.string.head_3),
            getString(R.string.head_4),
            getString(R.string.head_5),
            getString(R.string.head_6),
            getString(R.string.head_7),
            getString(R.string.head_8),
            getString(R.string.head_9),
            getString(R.string.head_10),
        )

        news = arrayOf(
            getString(R.string.news_a),
            getString(R.string.news_b),
            getString(R.string.news_c),
            getString(R.string.news_d),
            getString(R.string.news_e),
            getString(R.string.news_f),
            getString(R.string.news_g),
            getString(R.string.news_h),
            getString(R.string.news_i),
            getString(R.string.news_j),
        )
        for(i in imageId.indices){

            val news = News(imageId[i], heading[i])
            newsArrayList.add(news)
        }

    }
}
