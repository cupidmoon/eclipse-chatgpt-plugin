function addKeyCapture(element) {
	element.addEventListener('keydown', function(e) {
		if(e.key === 'Enter')
		{
			eclipseSendPrompt(element.innerText);
			element.setAttribute('contenteditable', 'false');
			element.removeEventListener('keydown');
		}
	});
}

window.addEventListener('load', function() {
    var element = document.getElementById("InitialInput")
	element.focus();
	addKeyCapture(element);
});