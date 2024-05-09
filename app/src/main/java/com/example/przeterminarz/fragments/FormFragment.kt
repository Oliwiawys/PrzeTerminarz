package com.example.przeterminarz.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.przeterminarz.data.ProductRepository
import com.example.przeterminarz.data.RepositoryLocator
import com.example.przeterminarz.databinding.FragmentFormBinding
import com.example.przeterminarz.model.FormType
import com.example.przeterminarz.model.Product
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


private const val TYPE_KEY = "type"

class FormFragment : Fragment() {
    private lateinit var type: FormType
    private lateinit var binding: FragmentFormBinding
    private lateinit var repository: ProductRepository
    private var edited: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = RepositoryLocator.productRepository
        arguments?.let {
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(TYPE_KEY, FormType::class.java)
            } else {
                it.getSerializable(TYPE_KEY) as? FormType
            } ?: FormType.New
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFormBinding.inflate(layoutInflater, container, false).also {
            binding = it
        }.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (type as? FormType.Edit)?.let {
            edited = repository.getProductById(it.id).also {
                with(binding.addProductName) {
                    setText(it.name)
                }
                with(binding.addExpirationDate) {
                    val dateTimeFormatter: DateTimeFormatter =
                        DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
                    val selectedDate = it.expirationDate.format(dateTimeFormatter)
                    setText(selectedDate)
                }
                with(binding.addCategory) {
                    val adapter = binding.addCategory.adapter
                    for (item in 0 until adapter.count) {
                        if (adapter.getItem(item) == it.category) {
                            setSelection(item)
                        }
                    }
                }
                with(binding.addQuantity) {
                    if (it.quantity != null)
                        setText(it.quantity.toString())
                }
            }
        }
        with(binding.button) {
            text = when (type) {
                is FormType.Edit -> "Zapisz"
                FormType.New -> "Dodaj"
            }
            setOnClickListener {
                if (validateData()) {
                    saveProduct((type as? FormType.Edit)?.id)
                    findNavController().popBackStack()
                }
            }
        }
        with(binding.addExpirationDate) {
            inputType = InputType.TYPE_NULL
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    showDatePickerDialog(this)
                }
                true
            }
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormatter.format(selectedDate.time)

                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun saveProduct(id: Int?) {
        val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())

        val productName = binding.addProductName.text.toString().trim()
        val expirationDate = LocalDate.parse(binding.addExpirationDate.text, dateTimeFormatter)
        val category = binding.addCategory.selectedItem.toString()

        val product: Product = if (binding.addQuantity.text.isNotEmpty()) {
            val quantity = binding.addQuantity.text.toString().toInt()
            edited?.copy(
                name = productName,
                expirationDate = expirationDate,
                category = category,
                quantity = quantity
            ) ?: Product(productName, expirationDate, category, quantity)
        } else {
            edited?.copy(
                name = productName,
                expirationDate = expirationDate,
                category = category
            ) ?: Product(productName, expirationDate, category)
        }
        if (id == null) {
            repository.add(product)
        } else {
            repository.set(id, product)
        }
    }

    private fun validateData(): Boolean {
        val dateTimeFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.getDefault())
        val productName = binding.addProductName.text.toString().trim()
        val expirationDateText = binding.addExpirationDate.text

        if (productName.isEmpty() || expirationDateText.isEmpty()) {
            binding.addProductName.error = "Proszę uzupełnić wszystkie wymagane pola"
            return false
        }

        val expirationDate = LocalDate.parse(binding.addExpirationDate.text, dateTimeFormatter)
        if (expirationDate.isBefore(LocalDate.now())) {
            binding.addExpirationDate.error = "Data nie może być przeszła"
            return false
        }

        return true
    }
}