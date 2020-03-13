package marshi.android.uicomponent.expandableview

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getDimensionOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import marshi.android.uicomponent.R
import marshi.android.uicomponent.animview.ElevationAnimView
import marshi.android.uicomponent.animview.relativeAnimator

class ExpandableItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ElevationAnimView {
  override val view
    get() = this

  private var elevationTo: Float = 0f
  private val expandPartView by lazy {
    val expandPartViews = this
      .children
      .filterIsInstance(ExpandPartView::class.java).toList()
    if (expandPartViews.size != 1) {
      throw IllegalStateException("ExpandPartView must equals to 1.")
    }
    expandPartViews.first()
  }
  private val dividerView by lazy {
    val dividerViews = this
      .children
      .filterIsInstance(DividerView::class.java).toList()
    if (1 < dividerViews.size) {
      throw IllegalStateException("DividerView must be less equals than 1.")
    }
    dividerViews.firstOrNull()
  }

  init {
    context.withStyledAttributes(attrs, R.styleable.ExpandableItemView, defStyleAttr, 0) {
      elevationTo = getDimensionOrThrow(R.styleable.ExpandableItemView_elevationTo)
    }
  }

  fun expand(animatorListener: Animator.AnimatorListener? = null) {
    val dividerShowAnimator = dividerView?.absoluteAnimator(1f)
    dividerView?.visibility = View.VISIBLE
    val expandAnimator = expandPartView.relativeAnimator(expandPartView.expandHeight)
    val elevationUpAnimator = relativeAnimator(elevationTo)
    val animators = listOfNotNull(dividerShowAnimator, expandAnimator, elevationUpAnimator)
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      playTogether(animators)
      animatorListener?.let {
        addListener(it)
      }
    }.start()
  }

  fun collapse(animatorListener: Animator.AnimatorListener? = null) {
    val dividerHideAnimator = dividerView?.absoluteAnimator(0f)
    val collapseAnimator = expandPartView.absoluteAnimator(0)
    val elevationDownAnimator = absoluteAnimator(0f)
    val animators = listOfNotNull(dividerHideAnimator, collapseAnimator, elevationDownAnimator)
    AnimatorSet().apply {
      interpolator = AccelerateDecelerateInterpolator()
      playTogether(animators)
      animatorListener?.let {
        addListener(it)
      }
    }.start()
  }
}

