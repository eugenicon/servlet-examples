<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<t:generic-page title="Forbidden" showNavBar="false" showFooter="false">
    <jsp:body>
        <style>
            body {
                background-color: #c3c4c4;
                overflow: hidden;
            }

            svg {
                height: 600px;
                position: absolute;
                width: 400px;
            }

            path {
                fill: transparent;
                stroke: rgba(33, 150, 243, 0.2);
                stroke-width: 0.5;
            }

            .wrapper {
                align-items: center;
                color: #323333;
                display: flex;
                font-family: monospace;
                font-size: 16px;
                height: 600px;
                justify-content: center;
                position: absolute;
                top: 50%;
                left: 50%;
                -webkit-transform: translateX(-50%) translateY(-50%);
                transform: translateX(-50%) translateY(-50%);
                width: 400px;
            }
            .wrapper h1, .wrapper p {
                position: absolute;
                z-index: 999;
            }
            .wrapper h1 {
                font-size: 2em;
                top: 70%;
            }
            .wrapper p {
                font-weight: 600;
                top: calc(70% + 2.5em);
            }

            .grid-lines {
                height: 100%;
                position: absolute;
                top: 0;
                left: 50%;
                -webkit-transform: translateX(-50%);
                transform: translateX(-50%);
                width: 100%;
            }

            .grid-lines__line {
                stroke-width: 0.1666666667;
            }

            .grid-lines__line--cir {
                stroke: rgba(18, 17, 17, 0.25);
            }

            .grid-lines__line--diag {
                stroke: rgba(18, 17, 17, 0.15);
            }

            .grid-lines__line--hor {
                stroke: rgba(18, 17, 17, 0.15);
            }

            .grid-lines__line--ver {
                stroke: rgba(18, 17, 17, 0.2);
            }

            .wiz {
                fill: transparent;
                stroke: rgba(255, 25, 13, 0.5);
            }

            .wiz--beard,
            .wiz--eye,
            .wiz--eye-brow,
            .wiz--hair,
            .wiz--hair-bg,
            .wiz--head,
            .wiz--mouth,
            .wiz--mustache,
            .wiz--staff,
            .wiz--sword {
                stroke: transparent;
            }

            .wiz--beard {
                fill: #efeeee;
                stroke: rgba(150, 153, 153, 0.5);
            }

            .wiz--eye {
                fill: #0b62a6;
                stroke: #044475;
            }

            .wiz--eye-brow {
                fill: #dcdddd;
                stroke: rgba(50, 51, 51, 0.5);
            }

            .wiz--hair {
                fill: #efeeee;
                stroke: rgba(150, 153, 153, 0.5);
            }

            .wiz--hair-bg {
                fill: #323333;
            }

            .wiz--head {
                fill: #ffcf9a;
            }

            .wiz--mouth {
                fill: #121111;
            }

            .wiz--mustache {
                fill: #dcdddd;
                stroke: rgba(50, 51, 51, 0.5);
            }

            .wiz--staff {
                fill: #323333;
                -webkit-transform: rotate(-45deg) translateX(30%) translateY(-10%);
                transform: rotate(-45deg) translateX(30%) translateY(-10%);
                -webkit-transform-origin: 50% 50%;
                transform-origin: 50% 50%;
            }

            .wiz--sword {
                fill: #969999;
                -webkit-transform: rotate(45deg) translateX(-35%) translateY(-5%);
                transform: rotate(45deg) translateX(-35%) translateY(-5%);
                -webkit-transform-origin: 50% 50%;
                transform-origin: 50% 50%;
            }
        </style>
        <div class="wrapper">

            <h1>403 Forbidden</h1>

            <p>You shall not pass!</p>

            <svg class="grid-lines clickable" onclick="navigate('welcome')"
                 viewbox="0 0 100 150"
                 preserveAspectRatio="xMidYMid slice"
                 height="150" width="100">

                <path class="grid-lines__line grid-lines__line--hor" d="m0,39 l100,0"/>
                <path class="grid-lines__line grid-lines__line--hor" d="m0,75 l100,0"/>
                <path class="grid-lines__line grid-lines__line--hor" d="m0,111 l100,0"/>
                <path class="grid-lines__line grid-lines__line--ver" d="m14,0 l0,150"/>
                <path class="grid-lines__line grid-lines__line--ver" d="m32,0 l0,150"/>
                <path class="grid-lines__line grid-lines__line--ver" d="m50,0 l0,150"/>
                <path class="grid-lines__line grid-lines__line--ver" d="m68,0 l0,150"/>
                <path class="grid-lines__line grid-lines__line--ver" d="m86,0 l0,150"/>
                <path class="grid-lines__line grid-lines__line--diag" d="m14,0 l72,150"/>
                <path class="grid-lines__line grid-lines__line--diag" d="m0,25 l100,100"/>
                <path class="grid-lines__line grid-lines__line--diag" d="m100,25 l-100,100"/>
                <path class="grid-lines__line grid-lines__line--diag" d="m86,0 l-72,150"/>
                <path class="grid-lines__line grid-lines__line--cir"
                      d="m50,57 c18,0 18,18 18,18
      c0,18 -18,18 -18,18
      c-18,0 -18,-18 -18,-18
      c0,-18 18,-18 18,-18"/>
                <path class="grid-lines__line grid-lines__line--cir"
                      d="m50,39 c36,0 36,36 36,36
      c0,36 -36,36 -36,36
      c-36,0 -36,-36 -36,-36
      c0,-36 36,-36 36,-36"/>
                <path class="grid-lines__line grid-lines__line--cir"
                      d="m50,21 c54,0 54,54 54,54
      c0,54 -54,54 -54,54
      c-54,0 -54,-54 -54,-54
      c0,-54 54,-54 54,-54"/>


                <path class="wiz wiz--hair-bg"
                      d="m50,48
      c0,0 6,0 6,2
      c0,0 0,3 2,3
      c0,0 2,0 2,4
      c0,0 2,0 2,4
      c0,0 3,0 3,6
      c0,0 2,4 -2,6
      c0,0 -2,4 -2,6
      c0,0 0,6 -3,6
      c0,0 0,0 -8,0
      c0,0 0,0, -8,0
      c0,0 -3,0 -3,-6
      c0,0 -2,-4 -2,-6
      c0,0 -4,-2 -2,-6
      c0,0 0,-6 3,-6
      c0,0 0,-4 2,-4
      c0,0 0,-4 2,-4
      c0,0 2,0 2,-3
      c0,0 6,0 6,-2"/>

                <path class="wiz wiz--head"
                      d="m50,50
      c0,0 8,0 8,10
      c0,0 0,10 -8,10
      c0,0 -8,0 -8,-10
      c0,0 0,-10 8,-10"/>

                <path class="wiz wiz--hair"
                      d="m50,48
      c0,0 6,0 6,2
      c0,0 0,3 2,3
      c0,0 2,0 2,4
      c0,0 2,0 2,4
      c0,0 3,0 3,6
      c0,0 2,4 -2,6
      c0,0 -2,4 -2,6
      c0,0 0,6 -3,6
      c0,0 0,0 -8,0
      c0,0 6,0 6,-2
      c0,0 4,0 2,-4
      c0,0 4,0 2,-4
      c0,0 -2,-2 2,-6
      c0,0 2,-4 -2,-6
      c0,0 -4,0 -4,-4
      c0,0 0,-4 -2,-4
      c0,0 0,-6 -4,-3"/>


                <path class="wiz wiz--hair"
                      d="m50,52
      c0,0 -6,-4 -6,4
      c0,0 0,4 -2,4
      c0,0 0,4 -2,4
      c0,0 -2,0 0,4
      c0,0 0,2 0,4
      c0,0 2,2 0,4
      c0,0 -2,4 4,3
      c0,0 -2,4 2,4
      c0,0 0,2 4,2
      c0,0 0,0, -8,0
      c0,0 -3,0 -3,-6
      c0,0 -2,-4 -2,-6
      c0,0 -4,-2 -2,-6
      c0,0 0,-6 3,-6
      c0,0 0,-4 2,-4
      c0,0 0,-4 2,-4
      c0,0 -1,0 0,-4
      c0,0 2,1 8,-1"/>

                <path class="wiz wiz--beard"
                      d="m50,69
      c0,0 5,0 5,-1
      c0,0 4,0 4,2
      c0,0 2,0 1,4
      c0,0 2,0 1,6
      c0,0 2,0 1,6
      c0,0 -2,4 -2,4
      c0,0 -2,4 0,6
      c0,0 -4,0 -4,-4
      c0,0 0,4 -2,4
      c0,0 0,2 -2,2
      c0,0 -4,0 -4,4
      c0,0 -4,0 -2,-6
      c0,0 -2,4 -6,4
      c0,0 4,-2 2,-6
      c0,0 -4,0 -4,-4
      c0,0 -4,0 -4,-4
      c0,0 4,4 4,-4
      c0,0 0,-6 2,-6
      c0,0 0,-4 2,-4
      c0,0 0,-4 2,-4
      c0,0 0,1 6,1"/>

                <path class="wiz wiz--mouth"
                      d="m50,65
      c0,0 5,0 5,3
      c0,0 0,2 -5,2
      c0,0 -5,0 -5,-2
      c0,0 0,-4 5,-3"/>

                <path class="wiz wiz--mustache"
                      d="m50,64
      c0,0 4,0 4,1
      c0,0 2,0 2,2
      c0,0 2,0 1,3
      c0,0 2,0 0,3
      c0,0 0,0 0,0
      c0,0 0,-2 -2,-2
      c0,0 1,-3 -1,-3
      c0,0 0,-1 -4,-2
      c0,0 -4,-2 -4,2
      c0,0 -1,0 -1,2
      c0,0 -2,0 -1,3
      c0,0 -4,-2 -1,-4
      c0,0 -2,-3 1,-3
      c0,0 -1,-5 6,-2"/>

                <path class="wiz wiz--eye wiz--left"
                      d="m45,60
       c0,0 2,0 2,1
       c0,0 0,1 -2,0
       c0,0 -1,0 0,-1"/>

                <path class="wiz wiz--eye wiz--right"
                      d="m52,60
       c0,0 1,-1 2,0
       c0,0 0,1 -2,1
       c0,0 -1,0 0,-1"/>

                <path class="wiz wiz--eye-brow wiz--left"
                      d="m42,61
       c0,0 0,-3 1,-3
       c0,0 3,0 4,2
       c0,0 -1,-1 -4,-1
       c0,0 -1,0 -1,2"/>

                <path class="wiz wiz--eye-brow wiz--right"
                      d="m51,60
       c0,0 2,-3 4,-3
       c0,0 2,0 2,3
       c0,0 -1,-2 -2,-2
       c0,0 -2,0 -4,2"/>

                <path class="wiz wiz--sword"
                      d="m50,30
       c0,0 2,2 2,6
       l0,50
       l6,0
       c0,0 2,0 2,-2
       l1,0
       c0,0 0,4 -4,4
       l-6,0
       l0,14
       c0,0 2,0 2,2
       c0,0 0,2 -4,2
       c0,0 -3,0 -4,-2
       c0,0 0,-2 2,-2
       l0,-14
       l-6,0
       c0,0 -4,0 -4,-4
       l1,0
       c0,0 0,2 2,2
       l6,0
       l0,-50
       c0,0 0,-3 2,-6
       z"/>

                <path class="wiz wiz--staff"
                      d="m46,30
       l2,0
       c0,0 1,0 1,1
       l0,2
       c0,0 2,0 2,-2
       l1,0
       c0,0 1,0 1,2
       c0,0 2,0 2,-2
       l2,0
       c0,0 0,4 -2,4
       c0,0 0,4 -2,4
       c0,0 0,2 -1,2
       l0,40
       c0,0 0,4 -1,4
       l0,40
       c0,0 0,4 -1,4
       c0,0 -1,0 -1,-4
       c0,0 -2,0 -2,-4
       l0,-60
       c0,0 -1,0 -1,-4
       l0,-20
       c0,0 -1,0 -1,-4
       c0,0 0,-3 1,-3"/>


            </svg>

        </div>
    </jsp:body>
</t:generic-page>

