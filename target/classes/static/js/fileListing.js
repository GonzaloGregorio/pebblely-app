function handleFileInputChange(fileInput, fileList, removeButtonClass) {
    fileInput.addEventListener('change', function (event) {
        const files = event.target.files;

        for (let i = 0; i < files.length; i++) {
            const listItem = document.createElement('li');
            const removeButton = document.createElement('span');
            removeButton.classList.add(removeButtonClass);
            removeButton.textContent = 'X';
            listItem.textContent = files[i].name;
            listItem.appendChild(removeButton);
            fileList.appendChild(listItem);
        }
    });
}

function handleRemoveButtonClick(fileList) {
    fileList.addEventListener('click', function (event) {
        if (event.target.classList.contains('remove-button-created') ||
            event.target.classList.contains('remove-button-upscale') ||
            event.target.classList.contains('remove-button-background')) {
            const listItem = event.target.parentNode;
            listItem.parentNode.removeChild(listItem);
        }
    });
}

// Usage
const createBackgroundFileInput = document.getElementById('createBackgroundFilesInput');
const createdFilesList = document.getElementById('selectedCreatedFilesList');
handleFileInputChange(createBackgroundFileInput, createdFilesList, 'remove-button-created');
handleRemoveButtonClick(createdFilesList);

const removeBackgroundFileInput = document.getElementById('removeBackgroundFilesInput');
const removedFilesList = document.getElementById('selectedRemovedFilesList');
handleFileInputChange(removeBackgroundFileInput, removedFilesList, 'remove-button-background');
handleRemoveButtonClick(removedFilesList);

const removeUpscaleFileInput = document.getElementById('removeUpscaleFilesInput');
const upscaleFilesList = document.getElementById('selectedUpscaleFilesList');
handleFileInputChange(removeUpscaleFileInput, upscaleFilesList, 'remove-button-upscale');
handleRemoveButtonClick(upscaleFilesList);

const colorCheckboxBackground = document.getElementById('colorCheckboxBackground');
const colorCheckboxInpaint = document.getElementById('colorCheckboxInpaint');
const styleColorBackground = document.getElementById('styleColorBackground');
const styleColorInpaint = document.getElementById('styleColorInpaint');

function toggleColorInput(checkbox, styleColor) {
    if (checkbox.checked) {
        styleColor.removeAttribute('hidden');
    } else {
        styleColor.setAttribute('hidden', 'hidden');
        styleColor.value = '';
    }
}

colorCheckboxBackground.addEventListener('change', () => {
    toggleColorInput(colorCheckboxBackground, styleColorBackground);
});

colorCheckboxInpaint.addEventListener('change', () => {
    toggleColorInput(colorCheckboxInpaint, styleColorInpaint);
});
