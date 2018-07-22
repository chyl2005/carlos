/***************************
 Labels
 ***************************/
var jqTransformGetLabel = function (objfield) {
    var selfForm = $(objfield.get(0).form);
    var oLabel = objfield.next();
    if (!oLabel.is('label')) {
        oLabel = objfield.prev();
        if (oLabel.is('label')) {
            var inputname = objfield.attr('id');
            if (inputname) {
                oLabel = selfForm.find('label[for="' + inputname + '"]');
            }
        }
    }
    if (oLabel.is('label')) { return oLabel.css('cursor', 'pointer'); }
    return false;
};
/* Hide all open selects */
var jqTransformHideSelect = function (oTarget) {
    var ulVisible = $('.jqTransformSelectWrapper ul:visible');
    ulVisible.each(function () {
        var oSelect = $(this).parents(".jqTransformSelectWrapper:first").find("select").get(0);
        //do not hide if click on the label object associated to the select
        if (!(oTarget && oSelect.oLabel && oSelect.oLabel.get(0) == oTarget.get(0))) { $(this).hide(); }
    });
};
/* Check for an external click */
var jqTransformCheckExternalClick = function (event) {
    if ($(event.target).parents('.jqTransformSelectWrapper').length === 0) { jqTransformHideSelect($(event.target)); }
};
/* Apply document listener */
var jqTransformAddDocumentListener = function () {
    $(document).mousedown(jqTransformCheckExternalClick);
};