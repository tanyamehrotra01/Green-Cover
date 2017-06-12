var getPixels = require('get-pixels') // npm install get-pixels

imageUrl = ""

getPixels(imageUrl, function(err, pixels) {

	// green = 203, 230, 163

	j = 0 // j will have the number of pixels which are green, do not really
		  // need this as map reduce of java will do it

	for (i = 0; i < pixels.data.length; i += 4)
	{
		console.log(pixels.data[i] + " " + pixels.data[i+1] + " " + pixels.data[i+2])
		
		if ((pixels.data[i] == 203) && (pixels.data[i+1] == 230) && (pixels.data[i+2] == 163))
		{
			j++;
		}
	}	

//	console.log(j)
})