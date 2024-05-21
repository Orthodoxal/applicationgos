package com.example.myapplication3.entity_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication3.R
import com.example.myapplication3.databinding.FragmentSecondBinding

class EntityInfoFragment : Fragment() {
    private val viewModel: EntityInfoViewModel by activityViewModels()

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val state = viewModel.state.value
            nameEditText.setText(state.name)
            priceEditText.setText(state.price)
            // FIXME(6: устанавливаем начальное значения поля на экране)
            confirmCheckbox.isChecked = state.contractConfirmed
            if (state.isEdit) {
                createOrUpdateButton.text = resources.getString(R.string.update)
                deleteButton.visibility = View.VISIBLE
            } else {
                createOrUpdateButton.text = resources.getString(R.string.create)
                deleteButton.visibility = View.GONE
            }

            createOrUpdateButton.setOnClickListener {
                viewModel.createOrUpdate()
                navigateBack()
            }

            deleteButton.setOnClickListener {
                viewModel.delete()
                navigateBack()
            }

            nameEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onNameChange(text?.toString().orEmpty())
            }

            priceEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onPriceChange(text?.toString().orEmpty())
            }

            // FIXME(8: добавить слушатель на изменение поля и пробрасываем в логику ViewModel)
            confirmCheckbox.setOnCheckedChangeListener { _, checked ->
                viewModel.onContractConfirmChange(checked)
            }
        }
    }

    private fun navigateBack() = findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
