package com.mialkan.composeaccessibilityorder

import android.content.Context
import android.text.InputType
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.view.ContextThemeWrapper

@Composable
fun AppDropdownMenu(
    modifier: Modifier = Modifier,
    defaultValue: String? = null,
    hint: String,
    items: List<String>,
    onSelect: (Int, String) -> Unit = { _, _ -> },
    enabled: Boolean = true,
    errorMessage: String? = null
) {
    AppTextInputLayout(
        modifier = modifier,
        value = defaultValue,
        hint = hint,
        enabled = enabled,
        isDropdownMenu = true,
        errorMessage = errorMessage,
        update = {
            it.showDropDownMenu(items, onItemSelectedAtPosition = { position ->
                onSelect(position, items[position])
            })
        }
    )
}
@Composable
fun AppTextInputLayout(
    modifier: Modifier,
    value: String?,
    hint: String,
    enabled: Boolean = true,
    isDropdownMenu: Boolean = false,
    errorMessage: String? = null,
    update: (TextInputLayout) -> Unit = { _ -> }
) {
    val style = if (isDropdownMenu) {
        R.style.Widget_App_TextInputLayout_ExposedDropdownMenu
    } else {
        R.style.Widget_App_TextInputLayout
    }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextInputLayout(
                ContextThemeWrapper(
                    context,
                    style
                )
            ).apply {
                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                if (isDropdownMenu) {
                    addView(
                        MaterialAutoCompleteTextView(
                            ContextThemeWrapper(
                                context,
                                style
                            )
                        )
                    )
                } else {
                    this.endIconMode = TextInputLayout.END_ICON_CUSTOM
                    this.setEndIconDrawable(R.drawable.ic_arrow_pointing_down)
                    val textInputEditText = TextInputEditText(
                        ContextThemeWrapper(
                            context,
                            style
                        )
                    ).apply {
                        this.isLongClickable = false
                        this.inputType = InputType.TYPE_NULL
                        this.isFocusable = false
                    }
                    addView(textInputEditText)
                }
            }
        },
        update = { textInputLayout ->
            textInputLayout.apply {
                this.hint = hint
                this.isEnabled = enabled
                this.setTextValue(value, false)
                this.error = errorMessage
                this.isErrorEnabled = errorMessage != null
            }
            update.invoke(textInputLayout)
        }
    )
}

fun TextInputLayout?.setTextValue(value: CharSequence?, animate: Boolean = true) {
    if (!animate) this?.isHintAnimationEnabled = false
    val editText = this?.editText
    if (editText is AutoCompleteTextView) {
        editText.setText(
            value,
            false
        )
    } else {
        editText?.setText(value)
    }
    if (!animate) this?.isHintAnimationEnabled = true
}

fun TextInputLayout.showDropDownMenu(
    options: List<String>,
    onItemSelectedAtPosition: (Int) -> Unit = { }
) {
    val adapter = DropDownArrayAdapter(context, android.R.layout.simple_list_item_1, options)
    val autoCompleteTextView = (editText as? AutoCompleteTextView)
    autoCompleteTextView?.setAdapter(adapter)
    autoCompleteTextView?.setOnItemClickListener { _, _, position, _ ->
        onItemSelectedAtPosition(position)
    }
}

class DropDownArrayAdapter(context: Context, val resource: Int, val options: List<String>) :
    ArrayAdapter<String>(context, resource, options) {

    private val noOpFilter = object : Filter() {
        private val noOpResult = FilterResults()
        override fun performFiltering(constraint: CharSequence?) = noOpResult
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
    }

    override fun getFilter() = noOpFilter
}