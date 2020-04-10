<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Fablix-CS122B</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8" />

 
    <!-- Bootstrap -->
    <!-- Bootstrap -->
    <link href="/assets/css/vendor/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/css/font-awesome.min.css" rel="stylesheet">
    
    <link rel="stylesheet" href="/assets/css/vendor/animate/animate.css">
    <link type="text/css" rel="stylesheet" media="all" href="/assets/js/vendor/mmenu/css/jquery.mmenu.all.css" />
    <link rel="stylesheet" href="/assets/js/vendor/videobackground/css/jquery.videobackground.css">
    <link rel="stylesheet" href="/assets/css/vendor/bootstrap-checkbox.css">
    
    <link rel="stylesheet" href="/assets/js/vendor/chosen/css/chosen.min.css">
    <link rel="stylesheet" href="/assets/js/vendor/chosen/css/chosen-bootstrap.css">
    <link rel="stylesheet" href="/assets/js/vendor/datatables/css/dataTables.bootstrap.css">
    <link rel="stylesheet" href="/assets/js/vendor/datatables/css/ColVis.css">
    <link rel="stylesheet" href="/assets/js/vendor/datatables/css/TableTools.css">
    
    <link href="/assets/css/minimal.css" rel="stylesheet">
    
    <link href="/assets/css/vendor/bootstrap/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/css/font-awesome.min.css" rel="stylesheet">
  </head>
  <body class="bg-1">

 



    <!-- Wrap all page content here -->
    <div id="wrap">

      


      <!-- Make page fluid -->
      <div class="row">
        




        <!-- Fixed navbar -->
        <div class="navbar navbar-default navbar-fixed-top navbar-transparent-black mm-fixed-top" role="navigation" id="navbar">
          


          <!-- Branding -->
          <div class="navbar-header col-md-2">
            <a class="navbar-brand" href="/">
              <strong>Fablix</strong>
            </a>
            <div class="sidebar-collapse">
              <a href="#">
                <i class="fa fa-bars"></i>
              </a>
            </div>
          </div>
          <!-- Branding end -->
          <div class="copyrights">Collect from <a href="https://github.com/yangchenxi"  title="CopyRights">CopyRights Chenxi Yang Jingwen Mo</a></div>


          <!-- .nav-collapse -->
          <div class="navbar-collapse">
                        
            <!-- Page refresh -->
            <ul class="nav navbar-nav refresh">
              <li class="divided">
                <a href="#" class="page-refresh"><i class="fa fa-refresh"></i></a>
              </li>
            </ul>
            <!-- /Page refresh -->

            <!-- Search -->
            <div class="search" id="main-search">
              <i class="fa fa-search"></i> <input type="text" placeholder="Search...">
            </div>
            <!-- Search end -->

            <!-- Quick Actions -->
            <ul class="nav navbar-nav quick-actions">


              <li class="dropdown divided user" id="current-user">

                <a class="dropdown-toggle options" data-toggle="dropdown" href="#">
                  Guest <i class="fa fa-caret-down"></i>
                </a>

              </li>

            </ul>
            <!-- /Quick Actions -->



            <!-- Sidebar -->
            <ul class="nav navbar-nav side-nav" id="sidebar">


              <li class="navigation" id="navigation">
                <a href="#" class="sidebar-toggle" data-toggle="#navigation">Navigation <i class="fa fa-angle-up"></i></a>
                
                <ul class="menu">
                  
                  <li class="active">
                    <a href="/movie/list">
                      <i class="fa fa-line-chart"></i> Top 20
    
                    </a>
                  </li>


                </ul>

              </li>

              
            </ul>
            <!-- Sidebar end -->





          </div>
          <!--/.nav-collapse -->





        </div>
        <!-- Fixed navbar end -->
        




        
        <!-- Page content -->
        <div id="content" class="col-md-12">
          








          <!-- page header -->
          <div class="pageheader">
            

            <h2><i class="fa fa-line-chart"></i>Movie Detail</h2>



          </div>
          <!-- /page header -->
          
          




          <!-- content main container -->
          <div class="main">
                <section class="tile color transparent-black">
                  <!-- tile body -->
                  <div class="tile-body color transparent-black rounded-corners">
                  
                    <div class="table-responsive">
                      <table class="table table-datatable table-custom" id="basicDataTable">
                        <thead>
                          <tr>
                            <th>Title</th>
                            <th>Year</th>
                            <th>Director</th>
                            <th>Genres</th>
                            <th>Stars</th>
                            <th>Rating</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <td>${movie.title}
                             </td>
                            <td>${movie.year}</td>
                            <td>${movie.director}</td>
                            <td>
    <c:forEach var="temp" items="${movie.genres}">


          ${temp.name}&nbsp


    </c:forEach>
                            </td>
                            <td>

    <c:forEach var="tempStar" items="${movie.stars}">
        <strong>
        <li>
        <c:url var="titleLink" value="/movie/stardetail">
            <c:param name="starId" value="${tempStar.id}"/>
        </c:url>

       
            <a href="${titleLink}">${tempStar.name}&nbsp</a>

        </li>
                                </strong>

    </c:forEach>

                            </td>
                            <td>${movie.rating}</td>
                          </tr>


                        </tbody>
                      </table>
                    </div>
                  </div>
                  <!-- /tile body -->
                </section>


          </div>
          <!-- /content container -->






        </div>
        <!-- Page content end -->




        </div>






      </div>
      <!-- Make page fluid-->




    </div>
    <!-- Wrap all page content end -->



    <section class="videocontent" id="video"></section>



    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="assets/js/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="assets/js/vendor/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="assets/js/vendor/mmenu/js/jquery.mmenu.min.js"></script>
    <script type="text/javascript" src="assets/js/vendor/sparkline/jquery.sparkline.min.js"></script>
    <script type="text/javascript" src="assets/js/vendor/nicescroll/jquery.nicescroll.min.js"></script>
    <script type="text/javascript" src="assets/js/vendor/animate-numbers/jquery.animateNumbers.js"></script>
    <script type="text/javascript" src="assets/js/vendor/videobackground/jquery.videobackground.js"></script>
    <script type="text/javascript" src="assets/js/vendor/blockui/jquery.blockUI.js"></script>

    <script src="assets/js/vendor/flot/jquery.flot.min.js"></script>
    <script src="assets/js/vendor/flot/jquery.flot.time.min.js"></script>
    <script src="assets/js/vendor/flot/jquery.flot.selection.min.js"></script>
    <script src="assets/js/vendor/flot/jquery.flot.animator.min.js"></script>
    <script src="assets/js/vendor/flot/jquery.flot.orderBars.js"></script>
    <script src="assets/js/vendor/easypiechart/jquery.easypiechart.min.js"></script>

    <script src="assets/js/vendor/rickshaw/raphael-min.js"></script> 
    <script src="assets/js/vendor/rickshaw/d3.v2.js"></script>
    <script src="assets/js/vendor/rickshaw/rickshaw.min.js"></script>

    <script src="assets/js/vendor/morris/morris.min.js"></script>

    <script src="assets/js/vendor/tabdrop/bootstrap-tabdrop.min.js"></script>

    <script src="assets/js/vendor/summernote/summernote.min.js"></script>

    <script src="assets/js/vendor/chosen/chosen.jquery.min.js"></script>

    <script src="assets/js/minimal.min.js"></script>

    <script>
    $(function(){

      // Initialize card flip
      $('.card.hover').hover(function(){
        $(this).addClass('flip');
      },function(){
        $(this).removeClass('flip');
      });

      // Initialize flot chart
      var d1 =[ [1, 715],
            [2, 985],
            [3, 1525],
            [4, 1254],
            [5, 1861],
            [6, 2621],
            [7, 1987],
            [8, 2136],
            [9, 2364],
            [10, 2851],
            [11, 1546],
            [12, 2541]
      ];
      var d2 =[ [1, 463],
                [2, 578],
                [3, 327],
                [4, 984],
                [5, 1268],
                [6, 1684],
                [7, 1425],
                [8, 1233],
                [9, 1354],
                [10, 1200],
                [11, 1260],
                [12, 1320]
      ];
      var months = ["January", "February", "March", "April", "May", "Juny", "July", "August", "September", "October", "November", "December"];

      // flot chart generate
      var plot = $.plotAnimator($("#statistics-chart"), 
        [
          {
            label: 'Sales', 
            data: d1,    
            lines: {lineWidth:3}, 
            shadowSize:0,
            color: '#ffffff'
          },
          { label: "Visits",
            data: d2, 
            animator: {steps: 99, duration: 500, start:200, direction: "right"},   
            lines: {        
              fill: .15,
              lineWidth: 0
            },
            color:['#ffffff']
          },{
            label: 'Sales',
            data: d1, 
            points: { show: true, fill: true, radius:6,fillColor:"rgba(0,0,0,.5)",lineWidth:2 }, 
            color: '#fff',        
            shadowSize:0
          },
          { label: "Visits",
            data: d2, 
            points: { show: true, fill: true, radius:6,fillColor:"rgba(255,255,255,.2)",lineWidth:2 }, 
            color: '#fff',        
            shadowSize:0
          }
        ],{ 
        
        xaxis: {

          tickLength: 0,
          tickDecimals: 0,
          min:1,
          ticks: [[1,"JAN"], [2, "FEB"], [3, "MAR"], [4, "APR"], [5, "MAY"], [6, "JUN"], [7, "JUL"], [8, "AUG"], [9, "SEP"], [10, "OCT"], [11, "NOV"], [12, "DEC"]],

          font :{
            lineHeight: 24,
            weight: "300",
            color: "#ffffff",
            size: 14
          }
        },
        
        yaxis: {
          ticks: 4,
          tickDecimals: 0,
          tickColor: "rgba(255,255,255,.3)",

          font :{
            lineHeight: 13,
            weight: "300",
            color: "#ffffff"
          }
        },
        
        grid: {
          borderWidth: {
            top: 0,
            right: 0,
            bottom: 1,
            left: 1
          },
          borderColor: 'rgba(255,255,255,.3)',
          margin:0,
          minBorderMargin:0,              
          labelMargin:20,
          hoverable: true,
          clickable: true,
          mouseActiveRadius:6
        },
        
        legend: { show: false}
      });

      $(window).resize(function() {
        // redraw the graph in the correctly sized div
        plot.resize();
        plot.setupGrid();
        plot.draw();
      });

      $('#mmenu').on(
        "opened.mm",
        function()
        {
          // redraw the graph in the correctly sized div
          plot.resize();
          plot.setupGrid();
          plot.draw();
        }
      );

      $('#mmenu').on(
        "closed.mm",
        function()
        {
          // redraw the graph in the correctly sized div
          plot.resize();
          plot.setupGrid();
          plot.draw();
        }
      );

      // tooltips showing
      $("#statistics-chart").bind("plothover", function (event, pos, item) {
        if (item) {
          var x = item.datapoint[0],
              y = item.datapoint[1];

          $("#tooltip").html('<h1 style="color: #418bca">' + months[x - 1] + '</h1>' + '<strong>' + y + '</strong>' + ' ' + item.series.label)
            .css({top: item.pageY-30, left: item.pageX+5})
            .fadeIn(200);
        } else {
          $("#tooltip").hide();
        }
      });

      
      //tooltips options
      $("<div id='tooltip'></div>").css({
        position: "absolute",
        //display: "none",
        padding: "10px 20px",
        "background-color": "#ffffff",
        "z-index":"99999"
      }).appendTo("body");

      //generate actual pie charts
      $('.pie-chart').easyPieChart();


      //server load rickshaw chart
      var graph;

      var seriesData = [ [], []];
      var random = new Rickshaw.Fixtures.RandomData(50);

      for (var i = 0; i < 50; i++) {
        random.addData(seriesData);
      }

      graph = new Rickshaw.Graph( {
        element: document.querySelector("#serverload-chart"),
        height: 150,
        renderer: 'area',
        series: [
          {
            data: seriesData[0],
            color: '#6e6e6e',
            name:'File Server'
          },{
            data: seriesData[1],
            color: '#fff',
            name:'Mail Server'
          }
        ]
      } );

      var hoverDetail = new Rickshaw.Graph.HoverDetail( {
        graph: graph,
      });

      setInterval( function() {
        random.removeData(seriesData);
        random.addData(seriesData);
        graph.update();

      },1000);

      // Morris donut chart
      Morris.Donut({
        element: 'browser-usage',
        data: [
          {label: "Chrome", value: 25},
          {label: "Safari", value: 20},
          {label: "Firefox", value: 15},
          {label: "Opera", value: 5},
          {label: "Internet Explorer", value: 10},
          {label: "Other", value: 25}
        ],
        colors: ['#00a3d8', '#2fbbe8', '#72cae7', '#d9544f', '#ffc100', '#1693A5']
      });

      $('#browser-usage').find("path[stroke='#ffffff']").attr('stroke', 'rgba(0,0,0,0)');

      //sparkline charts
      $('#projectbar1').sparkline('html', {type: 'bar', barColor: '#22beef', barWidth: 4, height: 20});
      $('#projectbar2').sparkline('html', {type: 'bar', barColor: '#cd97eb', barWidth: 4, height: 20});
      $('#projectbar3').sparkline('html', {type: 'bar', barColor: '#a2d200', barWidth: 4, height: 20});
      $('#projectbar4').sparkline('html', {type: 'bar', barColor: '#ffc100', barWidth: 4, height: 20});
      $('#projectbar5').sparkline('html', {type: 'bar', barColor: '#ff4a43', barWidth: 4, height: 20});
      $('#projectbar6').sparkline('html', {type: 'bar', barColor: '#a2d200', barWidth: 4, height: 20});

      // sortable table
      $('.table.table-sortable th.sortable').click(function() {
        var o = $(this).hasClass('sort-asc') ? 'sort-desc' : 'sort-asc';
        $('th.sortable').removeClass('sort-asc').removeClass('sort-desc');
        $(this).addClass(o);
      });

      //todo's
      $('#todolist li label').click(function() {
        $(this).toggleClass('done');
      });

      // Initialize tabDrop
      $('.tabdrop').tabdrop({text: '<i class="fa fa-th-list"></i>'});

      //load wysiwyg editor
      $('#quick-message-content').summernote({
        toolbar: [
          //['style', ['style']], // no style button
          ['style', ['bold', 'italic', 'underline', 'clear']],
          ['fontsize', ['fontsize']],
          ['color', ['color']],
          ['para', ['ul', 'ol', 'paragraph']],
          ['height', ['height']],
          //['insert', ['picture', 'link']], // no insert buttons
          //['table', ['table']], // no table button
          //['help', ['help']] //no help button
        ],
        height: 143   //set editable area's height
      });

      //multiselect input
      $(".chosen-select").chosen({disable_search_threshold: 10});
      
    })
      
    </script>
        <script>
    $(function(){

      // Add custom class to pagination div
      $.fn.dataTableExt.oStdClasses.sPaging = 'dataTables_paginate paging_bootstrap paging_custom';

      /*************************************************/
      /**************** BASIC DATATABLE ****************/
      /*************************************************/

      /* Define two custom functions (asc and desc) for string sorting */
      jQuery.fn.dataTableExt.oSort['string-case-asc']  = function(x,y) {
          return ((x < y) ? -1 : ((x > y) ?  1 : 0));
      };
       
      jQuery.fn.dataTableExt.oSort['string-case-desc'] = function(x,y) {
          return ((x < y) ?  1 : ((x > y) ? -1 : 0));
      };

      /* Add a click handler to the rows - this could be used as a callback */
      $("#basicDataTable tbody tr").click( function( e ) {
        if ( $(this).hasClass('row_selected') ) {
          $(this).removeClass('row_selected');
        }
        else {
          oTable01.$('tr.row_selected').removeClass('row_selected');
          $(this).addClass('row_selected');
        }

        // FadeIn/Out delete rows button
        if ($('#basicDataTable tr.row_selected').length > 0) {
          $('#deleteRow').stop().fadeIn(300);
        } else {
          $('#deleteRow').stop().fadeOut(300);
        }
      });

      /* Build the DataTable with third column using our custom sort functions */
      var oTable01 = $('#basicDataTable').dataTable({
        "sDom":
          "R<'row'<'col-md-6'l><'col-md-6'f>r>"+
          "t"+
          "<'row'<'col-md-4 sm-center'i><'col-md-4'><'col-md-4 text-right sm-center'p>>",
        "oLanguage": {
          "sSearch": ""
        },
        "aaSorting": [ [0,'asc'], [1,'asc'] ],
        "aoColumns": [
          null,
          null,
          { "sType": 'string-case' },
          null,
          null
        ],
        "fnInitComplete": function(oSettings, json) { 
          $('.dataTables_filter input').attr("placeholder", "Search");
        }
      });

      // Append delete button to table
      var deleteRowLink = '<a href="#" id="deleteRow" class="btn btn-red btn-xs delete-row">Delete selected row</a>'
      $('#basicDataTable_wrapper').append(deleteRowLink);

      /* Add a click handler for the delete row */
      $('#deleteRow').click( function() {
        var anSelected = fnGetSelected(oTable01);
        if (anSelected.length !== 0 ) {
          oTable01.fnDeleteRow(anSelected[0]);
          $('#deleteRow').stop().fadeOut(300);
        }
      });

      /* Get the rows which are currently selected */
      function fnGetSelected(oTable01Local){
        return oTable01Local.$('tr.row_selected');
      };

      /*******************************************************/
      /**************** INLINE EDIT DATATABLE ****************/
      /*******************************************************/

      function restoreRow (oTable02, nRow){
        var aData = oTable02.fnGetData(nRow);
        var jqTds = $('>td', nRow);
        
        for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
          oTable02.fnUpdate( aData[i], nRow, i, false );
        }
        
        oTable02.fnDraw();
      };

      function editRow (oTable02, nRow){
        var aData = oTable02.fnGetData(nRow);
        var jqTds = $('>td', nRow);
        jqTds[0].innerHTML = '<input type="text" value="'+aData[0]+'">';
        jqTds[1].innerHTML = '<input type="text" value="'+aData[1]+'">';
        jqTds[2].innerHTML = '<input type="text" value="'+aData[2]+'">';
        jqTds[3].innerHTML = '<input type="text" value="'+aData[3]+'">';
        jqTds[4].innerHTML = '<input type="text" value="'+aData[4]+'">';
        jqTds[5].innerHTML = '<a class="edit save" href="#">Save</a><a class="delete" href="#">Delete</a>';
      };

      function saveRow (oTable02, nRow){
        var jqInputs = $('input', nRow);
        oTable02.fnUpdate( jqInputs[0].value, nRow, 0, false );
        oTable02.fnUpdate( jqInputs[1].value, nRow, 1, false );
        oTable02.fnUpdate( jqInputs[2].value, nRow, 2, false );
        oTable02.fnUpdate( jqInputs[3].value, nRow, 3, false );
        oTable02.fnUpdate( jqInputs[4].value, nRow, 4, false );
        oTable02.fnUpdate( '<a class="edit" href="#">Edit</a><a class="delete" href="#">Delete</a>', nRow, 5, false );
        oTable02.fnDraw();
      };



      var oTable02 = $('#inlineEditDataTable').dataTable({
        "sDom":
          "R<'row'<'col-md-6'l><'col-md-6'f>r>"+
          "t"+
          "<'row'<'col-md-4 sm-center'i><'col-md-4'><'col-md-4 text-right sm-center'p>>",
        "oLanguage": {
          "sSearch": ""
        },
        "aoColumnDefs": [
          { 'bSortable': false, 'aTargets': [ "no-sort" ] }
        ],
        "fnInitComplete": function(oSettings, json) { 
          $('.dataTables_filter input').attr("placeholder", "Search");
        }
      });

      // Append add row button to table
      var addRowLink = '<a href="#" id="addRow" class="btn btn-green btn-xs add-row">Add row</a>'
      $('#inlineEditDataTable_wrapper').append(addRowLink);

      var nEditing = null;

      // Add row initialize
      $('#addRow').click( function (e) {
        e.preventDefault();

        // Only allow a new row when not currently editing
        if ( nEditing !== null ) {
          return;
        }
        
        var aiNew = oTable02.fnAddData([ '', '', '', '', '', '<a class="edit" href="">Edit</a>', '<a class="delete" href="">Delete</a>' ]);
        var nRow = oTable02.fnGetNodes(aiNew[0]);
        editRow(oTable02, nRow);
        nEditing = nRow;

        $(nRow).find('td:last-child').addClass('actions text-center');
      });

      // Delete row initialize
      $(document).on( "click", "#inlineEditDataTable a.delete", function(e) {
        e.preventDefault();
        
        var nRow = $(this).parents('tr')[0];
        oTable02.fnDeleteRow( nRow );
      });

      // Edit row initialize
      $(document).on( "click", "#inlineEditDataTable a.edit", function(e) {
        e.preventDefault();
         
        /* Get the row as a parent of the link that was clicked on */
        var nRow = $(this).parents('tr')[0];
         
        if (nEditing !== null && nEditing != nRow){
          /* A different row is being edited - the edit should be cancelled and this row edited */
          restoreRow(oTable02, nEditing);
          editRow(oTable02, nRow);
          nEditing = nRow;
        }
        else if (nEditing == nRow && this.innerHTML == "Save") {
          /* This row is being edited and should be saved */
          saveRow(oTable02, nEditing);
          nEditing = null;
        }
        else {
          /* No row currently being edited */
          editRow(oTable02, nRow);
          nEditing = nRow;
        }
      });

      /******************************************************/
      /**************** DRILL DOWN DATATABLE ****************/
      /******************************************************/

      var anOpen = [];

      var oTable03 = $('#drillDownDataTable').dataTable({
        "sDom":
          "R<'row'<'col-md-6'l><'col-md-6'f>r>"+
          "t"+
          "<'row'<'col-md-4 sm-center'i><'col-md-4'><'col-md-4 text-right sm-center'p>>",
        "oLanguage": {
          "sSearch": ""
        },
        "aoColumnDefs": [
          { 'bSortable': false, 'aTargets': [ "no-sort" ] }
        ],
        "aaSorting": [[ 1, "asc" ]],
        "bProcessing": true,
        "sAjaxSource": "assets/js/vendor/datatables/ajax/sources/objects.txt",
        "aoColumns": [
          {
            "mDataProp": null,
            "sClass": "control text-center",
            "sDefaultContent": '<a href="#"><i class="fa fa-plus"></i></a>'
          },
          { "mDataProp": "engine" },
          { "mDataProp": "browser" },
          { "mDataProp": "grade" }
        ],
        "fnInitComplete": function(oSettings, json) { 
          $('.dataTables_filter input').attr("placeholder", "Search");
        }
      });

      $(document).on( 'click', '#drillDownDataTable td.control', function () {
        var nTr = this.parentNode;
        var i = $.inArray( nTr, anOpen );

        $(anOpen).each( function () {
          if ( this !== nTr ) {
            $('td.control', this).click();
          }
        });
        
        if ( i === -1 ) {
          $('i', this).removeClass().addClass('fa fa-minus');
          $(this).parent().addClass('drilled');
          var nDetailsRow = oTable03.fnOpen( nTr, fnFormatDetails(oTable03, nTr), 'details' );
          $('div.innerDetails', nDetailsRow).slideDown();
          anOpen.push( nTr );
        }
        else {
          $('i', this).removeClass().addClass('fa fa-plus');
          $(this).parent().removeClass('drilled');
          $('div.innerDetails', $(nTr).next()[0]).slideUp( function () {
            oTable03.fnClose( nTr );
            anOpen.splice( i, 1 );
          } );
        }

        return false;
      });
       
      function fnFormatDetails( oTable03, nTr ){
        var oData = oTable03.fnGetData( nTr );
        var sOut =
          '<div class="innerDetails">'+
            '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
              '<tr><td>Rendering engine:</td><td>'+oData.engine+'</td></tr>'+
              '<tr><td>Browser:</td><td>'+oData.browser+'</td></tr>'+
              '<tr><td>Platform:</td><td>'+oData.platform+'</td></tr>'+
              '<tr><td>Version:</td><td>'+oData.version+'</td></tr>'+
              '<tr><td>Grade:</td><td>'+oData.grade+'</td></tr>'+
            '</table>'+
          '</div>';
        return sOut;
      };

      /****************************************************/
      /**************** ADVANCED DATATABLE ****************/
      /****************************************************/

      var oTable04 = $('#advancedDataTable').dataTable({
        "sDom":
          "<'row'<'col-md-4'l><'col-md-4 text-center sm-left'T C><'col-md-4'f>r>"+
          "t"+
          "<'row'<'col-md-4 sm-center'i><'col-md-4'><'col-md-4 text-right sm-center'p>>",
         "oLanguage": {
          "sSearch": ""
        },
        "oTableTools": {
          "sSwfPath": "assets/js/vendor/datatables/tabletools/swf/copy_csv_xls_pdf.swf",
          "aButtons": [
            "copy",
            "print",
            {
              "sExtends":    "collection",
              "sButtonText": 'Save <span class="caret" />',
              "aButtons":    [ "csv", "xls", "pdf" ]
            }
          ]
        },
        "fnInitComplete": function(oSettings, json) { 
          $('.dataTables_filter input').attr("placeholder", "Search");
        },
        "oColVis": {
          "buttonText": '<i class="fa fa-eye"></i>'
        }
      });

      $('.ColVis_MasterButton').on('click', function(){
        var newtop = $('.ColVis_collection').position().top - 45; 

        $('.ColVis_collection').addClass('dropdown-menu');
        $('.ColVis_collection>li>label').addClass('btn btn-default')     
        $('.ColVis_collection').css('top', newtop + 'px');
      });

      $('.DTTT_button_collection').on('click', function(){
        var newtop = $('.DTTT_dropdown').position().top - 45;   
        $('.DTTT_dropdown').css('top', newtop + 'px');
      });

      //initialize chosen
      $('.dataTables_length select').chosen({disable_search_threshold: 10});
      
    })
      
    </script>
  </body>
</html>
      
