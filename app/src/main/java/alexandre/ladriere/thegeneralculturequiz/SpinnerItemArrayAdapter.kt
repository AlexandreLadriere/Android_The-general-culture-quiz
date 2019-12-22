package alexandre.ladriere.thegeneralculturequiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.custom_spinner_item.view.*

class SpinnerItemArrayAdapter(ctx: Context, items: List<SpinnerItem>) : ArrayAdapter<SpinnerItem>(ctx, 0, items) {

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val spinnerItem = getItem(position)
        val view = recycledView ?: LayoutInflater.from(context).inflate(
            R.layout.custom_spinner_item,
            parent,
            false
        )
        view.spinner_item_title.text = spinnerItem!!.title
        return view
    }

    override fun getItem(position: Int): SpinnerItem? {
        return super.getItem(position)
    }
}

