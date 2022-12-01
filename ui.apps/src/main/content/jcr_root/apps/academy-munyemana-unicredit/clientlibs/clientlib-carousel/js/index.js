(function($) {
	$.validator.register({
		selector : "input.tab-title",
		validate : validate,
		show : show,
		clear : clear
	});
	function validate($el) {
		var multifieldItem = $el.closest('.coral3-Multifield-item');
		var val = Math.floor(1000 + Math.random() * 9000);
		var tabName = "tabpar_" + val;
		var parsysNameField = $(multifieldItem).find("input.tab-id");
		if (!parsysNameField.val()) {
			parsysNameField.val(tabName);
		}

		return;
	}
	function show($el, message) {
	}
	function clear($el) {
	}
}(jQuery));