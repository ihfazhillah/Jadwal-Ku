package com.ihfazh.jadwal_ku.screens.thumbnailview

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ihfazh.jadwal_ku.R
import com.ihfazh.jadwal_ku.screens.common.fragments.BaseFragment
import com.ihfazh.jadwal_ku.screens.common.views.ViewMvcFactory
import javax.inject.Inject

class ThumbnailViewFragment: BaseFragment() {
    lateinit var viewMvc: ThumbnailViewMvc
    @Inject lateinit var viewMvcFactory: ViewMvcFactory
    @Inject lateinit var controller: ThumbnailViewController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewMvc = viewMvcFactory.newThumbnailViewMvc(container)
        controller.bindViewMvc(viewMvc)
        return viewMvc.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageUrl = requireArguments().getString(IMAGE_URL)
        if (imageUrl != null){
            controller.bindImage(url = imageUrl)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
        enterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.explode);
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }

    companion object {
        const val IMAGE_URL = "image url"

        fun newInstance(url: String): ThumbnailViewFragment{
            val bundle = Bundle().apply {
                putString(IMAGE_URL, url)
            }

            val fragment = ThumbnailViewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}