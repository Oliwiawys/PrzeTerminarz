package com.example.przeterminarz.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.przeterminarz.R
import com.example.przeterminarz.adapters.ProductListAdapter
import com.example.przeterminarz.data.ProductRepository
import com.example.przeterminarz.data.RepositoryLocator
import com.example.przeterminarz.databinding.FragmentListBinding
import com.example.przeterminarz.model.FormType

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var productRepository: ProductRepository
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productRepository = RepositoryLocator.productRepository
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListBinding.inflate(layoutInflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        productListAdapter = ProductListAdapter(
            onItemClick = { position ->
                applyFilters()
                val product = productListAdapter.productList[position]
                val indexInRepository =
                    productRepository.getProductList().indexOfFirst { it == product }
                if (productListAdapter.productList[position].isExpired()) {
                    showProductExpiredDialog()
                } else {
                    findNavController().navigate(
                        R.id.action_listFragment_to_formFragment,
                        bundleOf("type" to FormType.Edit(indexInRepository))
                    )
                }
            },
            onLongItemClick = { position ->
                applyFilters()
                val product = productListAdapter.productList[position]
                val indexInRepository =
                    productRepository.getProductList().indexOfFirst { it == product }
                if (productListAdapter.productList[position].isExpired()) {
                    showProductExpiredDialog()
                } else {
                    showDeleteConfirmationDialog(indexInRepository)
                }
            }
        )

        binding.productList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }

        productListAdapter.productList = productRepository.getProductList()

        binding.add.setOnClickListener {
            findNavController()
                .navigate(
                    R.id.action_listFragment_to_formFragment
                )
        }

        binding.categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                applyFilters()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.expired.setOnCheckedChangeListener { _, _ ->
            applyFilters()
        }
    }

    private fun applyFilters() {
        val selectedCategory = binding.categories.selectedItem.toString()
        val isExpiredChecked = binding.expired.isChecked
        val isValidChecked = binding.valid.isChecked

        val filteredList = if (selectedCategory == "Wszystkie") {
            if (isExpiredChecked) {
                productRepository.getProductList().filter { it.isExpired() }
            } else if (isValidChecked) {
                productRepository.getProductList().filterNot { it.isExpired() }
            } else {
                productRepository.getProductList()
            }
        } else {
            if (isExpiredChecked) {
                productRepository.getProductListByCategory(selectedCategory)
                    .filter { it.isExpired() }
            } else if (isValidChecked) {
                productRepository.getProductListByCategory(selectedCategory)
                    .filterNot { it.isExpired() }
            } else {
                productRepository.getProductListByCategory(selectedCategory)
            }
        }

        val sortedList = filteredList.sortedBy { it.expirationDate }

        binding.summary.text = "Liczba produktów: ${sortedList.size}"
        productListAdapter.productList = sortedList
    }

    private fun showDeleteConfirmationDialog(id: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Potwierdzenie usunięcia")
            .setMessage("Czy na pewno chcesz usunąć ten produkt?")
            .setPositiveButton("Tak") { _, _ ->
                deleteProduct(id)
            }
            .setNegativeButton("Nie", null)
            .show()
    }

    private fun showProductExpiredDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Produkt przeterminowany")
            .setMessage("Nie można usuwać ani edytować przeterminowanego produktu.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun deleteProduct(id: Int) {
        productRepository.removeById(id)
        productListAdapter.productList = productRepository.getProductList()
        applyFilters()
    }

    private fun onDstChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.listFragment) {
            productListAdapter.productList = productRepository.getProductList()
        }
    }

    override fun onStart() {
        super.onStart()
        findNavController().addOnDestinationChangedListener(::onDstChanged)
    }

    override fun onStop() {
        findNavController().removeOnDestinationChangedListener(::onDstChanged)
        super.onStop()
    }
}