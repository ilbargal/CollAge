(function($) {
    skel.init({
        		reset: 'full',
        		breakpoints: {
        			global: {
        				href: 'css/style.css',
        				containers: 1400,
        				grid: { gutters: ['2em', 0] }
        			},
        			xlarge: {
        				media: '(max-width: 1680px)',
        				href: 'css/style-xlarge.css',
        				containers: 1200
        			},
        			large: {
        				media: '(max-width: 1280px)',
        				href: 'css/style-large.css',
        				containers: 960,
        				grid: { gutters: ['1.5em', 0] },
        				viewport: { scalable: false }
        			},
        			medium: {
        				media: '(max-width: 980px)',
        				href: 'css/style-medium.css',
        				containers: '90%'
        			},
        			small: {
        				media: '(max-width: 736px)',
        				href: 'css/style-small.css',
        				containers: '90%',
        				grid: { gutters: ['1.25em', 0] }
        			},
        			xsmall: {
        				media: '(max-width: 480px)',
        				href: 'css/style-xsmall.css',
        			}
        		},
        		plugins: {
        			layers: {
        				config: {
        					mode: 'transform'
        				},
        				navPanel: {
        					animation: 'pushX',
        					breakpoints: 'medium',
        					clickToHide: false,
        					height: '100%',
        					hidden: true,
        					html: '<div data-action="moveElement" data-args="navLeft"></div>',
        					orientation: 'vertical',
        					position: 'top-right',
        					side: 'right',
        					width: 250
        				},
        				navButton: {
        					breakpoints: 'medium',
        					height: '4em',
        					html: '<span class="toggle" data-action="toggleLayer" data-args="navPanel"></span>',
        					position: 'top-right',
        					side: 'top',
        					width: '6em'
        				}
        			}
        		}
        	});

	$(function() {

		$(window).on("scroll", function() {
			if($(window).scrollTop() > 50) {
				$("#header").addClass("header-active");
			} else {
				//remove the background property so it comes transparent again (defined in your css)
				$("#header").removeClass("header-active");
			}
		});

		// Animate Products
		var animatedActivities = function() {
			if ( $('#fh5co-products').length > 0 ) {
				$('#fh5co-products .to-animate').each(function( k ) {

					var el = $(this);

					setTimeout ( function () {
						el.addClass('bounceIn animated');
					},  k * 200, 'easeInOutExpo' );

				});
			}
		};
	});

	hello.init({
      facebook : '1183068808406821',
      google   : '12345'
    },{
      redirect_uri : '/main'
    })
})(jQuery);