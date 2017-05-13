/**
 * Created by rishi on 4/23/17.
 */

$('.upload-btn').on('click', function (){
    $('#upload-input').click();
    $('.progress-bar').text('0%');
    $('.progress-bar').width('0%');
    $('#placeholder').show();
    $('#output-img > img').remove();
    $('#output-img').hide();
});

$('#upload-input').on('change', function(){

    var files = $(this).get(0).files;

    if (files.length > 0){
        // create a FormData object which will be sent as the data payload in the
        // AJAX request
        var formData = new FormData();

        // loop through all the selected files and add them to the formData object
        for (var i = 0; i < files.length; i++) {
            var file = files[i];

            // add the files to formData object for the data payload
            formData.append('uploads[]', file, file.name);
        }

        $.ajax({
            url: '/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(data){
                $('.upload-btn').hide();
                $('.submit-btn').show();
            },
            xhr: function() {
                // create an XMLHttpRequest
                var xhr = new XMLHttpRequest();

                // listen to the 'progress' event
                xhr.upload.addEventListener('progress', function(evt) {

                    if (evt.lengthComputable) {
                        // calculate the percentage of upload completed
                        var percentComplete = evt.loaded / evt.total;
                        percentComplete = parseInt(percentComplete * 100);

                        // update the Bootstrap progress bar with the new percentage
                        $('.progress-bar').text(percentComplete + '%');
                        $('.progress-bar').width(percentComplete + '%');

                        // once the upload reaches 100%, set the progress bar text to done
                        if (percentComplete === 100) {
                            $('.progress-bar').html('Done');
                        }

                    }

                }, false);

                return xhr;
            }
        });

    }
});


$('.submit-btn').on('click', function () {
    $.ajax({
        url: '/generate',
        type: 'get',
        data: '',
        processData: false,
        contentType: false,
        success: function(res){
            if(res.status == 200){
                $('.submit-btn').hide();
                $('.upload-btn').show();
                $('#placeholder').hide();
                var imageName = res.data + '.png';
                $('#output-img').append("<img src='' class='img-fluid uml-output' alt='Output image'>");
                $('.uml-output').attr('src', imageName);
                $('#output-img').show();
            }else {
                console.log('UML Class diagram generation failed');
            }

        }
    });
});
