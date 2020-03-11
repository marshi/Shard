package marshi.android.uicomponent.customview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import marshi.android.uicomponent.animview.ElevationAnimView

class ExpandableItemConstraintLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ElevationAnimView {
  override val view
    get() = this

//  private val binding = ExpandableItemViewBinding.inflate(LayoutInflater.from(context), this, true)
}
