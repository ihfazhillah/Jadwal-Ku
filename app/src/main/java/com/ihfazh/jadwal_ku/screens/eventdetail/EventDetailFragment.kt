package com.ihfazh.jadwal_ku.screens.eventdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.screens.common.fragments.BaseFragment
import com.ihfazh.jadwal_ku.screens.common.views.ViewMvcFactory
import javax.inject.Inject

class EventDetailFragment: BaseFragment() {
    lateinit var mvcView: EventDetailMvcView
    @Inject lateinit var mvcFactory: ViewMvcFactory
    @Inject lateinit var detailController: EventDetailController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mvcView = mvcFactory.newEventDetailMvc(container)
        detailController.bindView(mvcView, getEventDetailId())
        return mvcView.rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        detailController.onStart()
    }

    override fun onStop() {
        super.onStop()
        detailController.onStop()
    }

    private fun getEventDetailId(): String {
        return requireArguments().getString(EVENT_DETAIL_ID)!!
    }

    companion object {
        const val EVENT_DETAIL_ID = "EVENT_DETAIL_ID"
        fun newInstance(eventId: String): EventDetailFragment {
            val bundle = Bundle()
            bundle.putString(EVENT_DETAIL_ID, eventId)

            val fragment = EventDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}