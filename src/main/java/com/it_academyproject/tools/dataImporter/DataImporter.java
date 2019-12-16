package com.it_academyproject.tools.dataImporter;

import com.it_academyproject.Domains.*;

import com.it_academyproject.repositories.*;

import com.it_academyproject.tools.excel.Excel;

import org.apache.poi.ss.usermodel.DateUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.*;
@Service
public class DataImporter
{
    @Autowired
    MyAppUserRepository myAppUserRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AbsenceRepository absenceRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ItineraryRepository itineraryRepository;
    @Autowired
    ExerciceRepository exerciceRepository;
    @Autowired
    StatusExerciceRepository statusExerciceRepository;
    @Autowired
    UserExerciceRepository userExerciceRepository;
    @Autowired
    SeatRepository seatRepository;


    public boolean manualCreation ()
    {
        //role
        Map<Integer , String >rolesList = new HashMap<>();
        rolesList.put( 1 , "ROLE_STUDENT");
        rolesList.put( 2 , "ROLE_TEACHER");
        rolesList.put( 3 , "ROLE_ADMIN");
        rolesList.put( 4 , "ROLE_IT");

        for (int i = 1; i <= rolesList.size(); i++)
        {
            Role role = new Role();
            role.setId( i );
            role.setName( rolesList.get( i ) );
            roleRepository.save( role );
        }
        //Itinerary
        Map<Integer , String >itineraryList = new HashMap<>();
        itineraryList.put( 1 , "COMMON-BLOCK");
        itineraryList.put( 2 , "FRONT-END");
        itineraryList.put( 3 , "BACK-END - JAVA");
        itineraryList.put( 4 , "BACK-END - .NET");
        for (int i = 1; i <= itineraryList.size(); i++)
        {
            Itinerary itinerary = new Itinerary();
            itinerary.setId( i );
            itinerary.setName( itineraryList.get( i ) );
            itineraryRepository.save( itinerary );
        }
        //status_exercice
        Map<Integer , String >exStatusList = new HashMap<>();
        exStatusList.put( 1 , "NONE");
        exStatusList.put( 2 , "TURNED IN");
        exStatusList.put( 3 , "RECEIVED - PENDING OF REVISION");
        exStatusList.put( 4 , "CHECKED - PENDING OF DISCUSSION");
        exStatusList.put( 5 , "FINISHED");

        for (int i = 1; i <= exStatusList.size(); i++)
        {
            StatusExercice statusExercice = new StatusExercice();
            statusExercice.setId( i );
            statusExercice.setName( exStatusList.get( i ) );
            statusExerciceRepository.save( statusExercice );
        }

        return false;
    }

    public boolean importAlumnesActius ()
    {
        /* DummyContentUtil dummyContentUtil = new DummyContentUtil( myAppUserRepository );
        dummyContentUtil.generateDummyUsers();*/
        Role role = roleRepository.findOneById ( 1 );
        Itinerary itinerary = itineraryRepository.findOneById ( 1 );
        try {
            System.out.println(Paths.get("").toAbsolutePath().toString());
            String fileLocation = "AlumnesActius.xls";
            Excel excel = new Excel();
            excel.openFile( fileLocation );
            Map<Integer, List<String>> excelContent = excel.readJExcelContent( 0 );
            //System.out.println( excelContent );

            // Get a set of the entries
            Set set = excelContent.entrySet();
            List<String> currentRow;
            String currentCell;

            for (Integer i : excelContent.keySet())
            {
                //create the objects that will be placed in the data base
               MyAppUser myAppUser = new MyAppUser();
                Course course = new Course();
                currentRow = excelContent.get(i);

                if (!currentRow.get(0).equals(""))
                {
                    for (int j = 0; j < currentRow.size() ; j++)
                    {
                        currentCell = currentRow.get(j);
                        if (i==0) //title row
                        {

                        }
                        else
                        {
                            if ( j == 0) //name and last name this need to be separeated by the coma
                            {
                                int commaPos = currentCell.indexOf(",");
                                String lastName = currentCell.substring(0 , commaPos);
                                String name = currentCell.substring(commaPos+2 , currentCell.length());
                                myAppUser.setFirstName( name );
                                myAppUser.setLastName (lastName);
                            }
                            else if ( j == 1) //Núm. Document
                            {
                                myAppUser.setIdDocument (currentCell);
                            }
                            else if ( j == 2) //Núm. Document
                            {
                                /*
                                PREVIOUSLY USED TO ESTIMATE THE BIRTHDAY, REMOVED FOR AGE.
                                Calendar cal = Calendar.getInstance();
                                cal.set(2019-Integer.parseInt(currentCell), 1, 1);

                                 */
                                try
                                {
                                    myAppUser.setAge ( Integer.parseInt(currentCell) );
                                }
                                catch ( Exception e )
                                {
                                    System.out.println(e.getLocalizedMessage());
                                    //myAppUser.setAge( null );
                                }

                            }
                            else if ( j == 3) //Núm. Document
                            {
                                myAppUser.setEmail ( currentCell );
                            }
                            else if ( j == 4) //Núm. Document
                            {
                                if ( currentCell.toLowerCase().equals("h"))
                                {
                                    myAppUser.setGender ( 'M' );
                                }
                                else if (( currentCell.toLowerCase().equals("d")) || ( currentCell.toLowerCase().equals("m")))
                                {
                                    myAppUser.setGender ( 'F' );
                                }
                                else
                                {
                                    System.out.println(currentCell + " - why is it empty");
                                }

                            }
                            else if ( j == 5)
                            {
                                if (currentCell.equals(""))
                                {
                                    course.setBeginDate( null );
                                    course.setEndDate(null);
                                    course.setUserStudent(myAppUser);
                                }
                                else
                                {
                                    course.setBeginDate( stringToDate( currentCell) );
                                    course.setEndDate(null);
                                    course.setUserStudent(myAppUser);
                                }

                            }
                            else if ( j == 6)
                            {
                                if (! currentCell.equals(""))
                                {
                                    course.setEndDate( stringToDate(currentCell) );
                                }
                            }
                            else if ( j == 9)
                            {
                                //check if is the information in the file or duplicated
                                //myAppUser
                                if ( myAppUserRepository.findByEmail(myAppUser.getEmail()) == null )
                                {
                                    myAppUser.setId();
                                    myAppUser.setRole( role );
                                    myAppUserRepository.save( myAppUser);
                                }
                                else
                                {
                                    myAppUser = myAppUserRepository.findByEmail(myAppUser.getEmail());
                                    course.setUserStudent( myAppUser );
                                }
                                //course
                                List<Course> courseList = courseRepository.findByUserStudent( myAppUser );
                                //System.out.println( courseList );
                                //check if the student has a course
                                if (( courseList == null ) || ( courseList.size() == 0 ))
                                {
                                    course.setItinerary( itinerary );
                                    courseRepository.save(course);
                                }
                                if (! currentCell.equals(""))
                                {
                                    //set the absence days
                                    String[] array = currentCell.split(", ", -1);

                                    for (int k = 0; k < array.length; k++)
                                    {
                                        Absence absence = new Absence();
                                        absence.setDateMissing( stringToDate(array[k]));
                                        if ( absence.getDateMissing() != null )
                                        {
                                            absence.setUserStudent( myAppUser );
                                            //absence
                                            List<Absence> absenceList = absenceRepository.findByUserStudentAndDateMissing( myAppUser , absence.getDateMissing());
                                            if (( absenceList == null ) ||  (absenceList.size() == 0 ))
                                            {
                                                //System.out.println(absence);
                                                absenceRepository.save(absence);
                                            }
                                            else
                                            {
                                                System.out.println(absenceList);
                                            }
                                        }
                                    }
                                }


                            }




                        }
                    }
                }
                else
                {
                    System.out.print("Empty - ");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public JSONObject importEjerciciosporalumno ( int sheetNumber , int itineraryNumber )
    {
        List <String> NotFoundUser = new ArrayList<>();
        Role role = roleRepository.findOneById ( 1 );
        Itinerary itinerary = itineraryRepository.findOneById ( 1 );
        try
        {
            System.out.println(Paths.get("").toAbsolutePath().toString());
            String fileLocation = "Ejerciciosporalumno.xls";
            Excel excel = new Excel();
            excel.openFile(fileLocation);
            Map<Integer, List<String>> excelContent = excel.readJExcelContent(sheetNumber);
            Map<Integer , Exercice> exerciceMap = new HashMap<>();
            //System.out.println( excelContent );

            // Get a set of the entries
            Set set = excelContent.entrySet();
            List<String> currentRow;
            Map<Integer , Integer> keyList = new HashMap<>();
            for (Integer i : excelContent.keySet())
            {
                Course course = new Course();
                currentRow = excelContent.get(i);
                MyAppUser myAppUser = null;
                for (int j = 0; j < currentRow.size() ; j++)
                {

                    String currentCell = currentRow.get( j );
                    //title row
                    if ( i == 0 )
                    {
                        //CREAMOS LAS TAREAS
                        if ( j == 0 )
                        {
                            //Starting date / useless
                        }
                        else if ( j == 1 )
                        {
                            //Nothing
                        }
                        else if (( j > 1 ) && ( j < 16))
                        {
                            //Nothing
                            //check if is on the DB
                            List<Exercice> exercicesList = exerciceRepository.findAllByNameAndItinerary( currentCell , itineraryRepository.findOneById( itineraryNumber ) );
                            if ( (exercicesList == null ) || ( exercicesList.size() == 0) )
                            {
                                //exists do nothing

                                Exercice exercice = new Exercice();
                                exercice.setItinerary( itineraryRepository.findOneById( itineraryNumber ) );
                                exercice.setName( currentCell );
                                exerciceMap.put( j , exercice );
                                exercice  = exerciceRepository.save( exercice );
                                keyList.put( j , exercice.getId());

                            }
                            else if ( exercicesList.size() > 0 )
                            {
                                keyList.put( j , exercicesList.get(0).getId());
                            }


                        }
                    }
                    else
                    {

                        if ( j == 1 )
                        {
                            if ( ! currentCell.equals(""))
                            {
                                //NAME AND LAST NAME
                                int commaPos = currentCell.indexOf(",");
                                String lastName = currentCell.substring(0 , commaPos);
                                String name = currentCell.substring(commaPos+2 , currentCell.length());
                                List<MyAppUser> myAppUserList = myAppUserRepository.findByFirstNameAndLastName ( name , lastName );
                                if ((myAppUserList == null ) || ( myAppUserList.size() == 0 ))
                                {
                                    //user not found so we are going to look for contains
                                    //look by name
                                    List<MyAppUser> myAppUserList1 = (myAppUserRepository.findByFirstName( name ));
                                    if ((myAppUserList1 != null ) && ( myAppUserList1.size() != 0 ))
                                    {
                                        //check if it contains the last name
                                        for (int k = 0; k < myAppUserList1.size() ; k++)
                                        {
                                            System.out.println( "357 - " + myAppUserList1.get(k).getFirstName() +  " " +  myAppUserList1.get(k).getLastName());
                                            if ( myAppUserList1.get(k).getLastName().indexOf( lastName ) > 0 )
                                            {
                                                //keeper contains last name
                                                myAppUser = myAppUserList1.get(k);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        //throw (new UserNotFoundException(currentCell));
                                        NotFoundUser.add(currentCell);
                                    }

                                }
                                else if (( myAppUserList != null ) && ( myAppUserList.size() == 1 ))
                                {
                                    //ok only finding one
                                    myAppUser = myAppUserList.get(0);
                                    //
                                }

                            }
                        }
                        else if (( j > 1 ) && ( j < 16) && ( ! currentCell.equals("")))
                        {
                            Integer exerciceKey = keyList.get(j);
                            //check if there is an excercise associated with this
                            Exercice exercice = exerciceRepository.findOneById( exerciceKey );
                            if ( exercice  == null  )
                            {
                                throw (new Exception ("The exercise is not created. "));
                            }
                            else
                            {
                                //check if is the user_exercice
                                UserExercice userExercice = userExerciceRepository.findOneByUserStudentAndExercice( myAppUser , exercice );
                                if ( userExercice == null )
                                {
                                    userExercice = new UserExercice();
                                    userExercice.setComments("Imported Automatically");

                                    Date javaDate = null;
                                    try
                                    {
                                        javaDate = DateUtil.getJavaDate( Double.parseDouble( currentCell ));
                                        userExercice.setDate_status( javaDate );
                                    }
                                    catch (NumberFormatException e)
                                    {
                                        //e.printStackTrace();
                                        if ( currentCell.length( ) == 5 )
                                        {
                                            userExercice.setDate_status( stringToDate( currentCell + "/2019"));
                                        }
                                    }
                                    //System.out.print(new SimpleDateFormat("dd/MM/yyyy").format(javaDate) + " - ");


                                    userExercice.setExercice( exercice );
                                    userExercice.setStatusExercice( statusExerciceRepository.findOneById( 5 ) );
                                    userExercice.setUserStudent( myAppUser );
                                    if ( userExercice.getDate_status() != null )
                                        userExerciceRepository.save ( userExercice );
                                        List<Course> thisUsersCourseList = courseRepository.findByUserStudent(myAppUser);
                                        if ( itineraryNumber != 1 )
                                        {
                                            if (( thisUsersCourseList != null ) && ( thisUsersCourseList.size() > 0))
                                            {
                                                if ( thisUsersCourseList.get(0).getItinerary() != null )
                                                {
                                                    Course thisCourse = thisUsersCourseList.get(0);
                                                    thisCourse.setItinerary( itineraryRepository.findOneById( itineraryNumber ) );
                                                    courseRepository.save( thisCourse );
                                                }
                                            }
                                        }

                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        JSONObject sendData = new JSONObject();
        sendData.put( "NotInStudents" , NotFoundUser );
        return sendData;
    }
    public boolean importTaules ()
    {
        List <String> NotFoundUser = new ArrayList<>();
        Role role = roleRepository.findOneById ( 1 );
        Itinerary itinerary = itineraryRepository.findOneById ( 1 );
        try {
            System.out.println(Paths.get("").toAbsolutePath().toString());
            String fileLocation = "Taules.xls";
            Excel excel = new Excel();
            excel.openFile(fileLocation);
            Map<Integer, List<String>> excelContent = excel.readJExcelContent(0);
            Map<Integer, Exercice> exerciceMap = new HashMap<>();
            //System.out.println( excelContent );

            // Get a set of the entries
            Set set = excelContent.entrySet();
            List<String> currentRow;

            for (Integer i : excelContent.keySet())
            {
                Course course = new Course();
                currentRow = excelContent.get(i);
                MyAppUser myAppUser = null;
                if ( ( i == 0) || ( i == 2)|| ( i == 4)|| ( i == 6) )
                {
                    for (int j = 0; j < currentRow.size(); j++) {
                        String currentCell = currentRow.get(j);

                        if ( ! currentCell.equals(""))
                        {

                            int commaPos = currentCell.indexOf(",");
                            String lastName = null;
                            try {
                                lastName = currentCell.substring(0 , commaPos);
                                String name = currentCell.substring(commaPos+2 , currentCell.length());
                                List<MyAppUser> myAppUserList = myAppUserRepository.findByFirstNameAndLastName( name , lastName);
                                if ( myAppUserList.size() == 0)
                                {
                                    System.out.println( "currentCell + " + currentCell );

                                }
                                else
                                {
                                    myAppUser = myAppUserList.get(0);
                                    int row = (i/2) + 1;
                                    int col = j + 1;
                                    System.out.println( "row - " + row + " col - " + col );
                                    List<Seat> seatList = seatRepository.findByRowNumberAndColNumber( row , col );
                                    if ( seatList.size() > 0)
                                    {
                                        for (int k = 0; k < seatList.size() ; k++)
                                        {
                                            Seat seat = seatList.get(i);
                                            myAppUser.setSeat (seat);
                                        }
                                    }
                                    else
                                    {
                                        Seat seat = new Seat ();
                                        seat.setRowNumber( row );
                                        seat.setColNumber( col );
                                        seat = seatRepository.save(seat);
                                        myAppUser.setSeat (seat);
                                    }

                                }
                            }
                            catch (Exception e)
                            {
                                System.out.println("No comma - " + currentCell );
                            }



                        }
                    }
                }

            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return true;
    }
    private Date stringToDate (String stringDate )
    {
        String OriginalString = stringDate.toString();
        if ( ( stringDate.length() > 7) )
        {
            if ( stringDate.indexOf(",") > 0)
            {
                stringDate = stringDate.replaceAll("," , "");
            }
            Calendar cal = Calendar.getInstance();
            int firstDivider = stringDate.indexOf("/");
            Integer day = null;
            try {
                day = Integer.parseInt( stringDate.substring(0 , firstDivider) );
            }
            catch (NumberFormatException e)
            {
                System.out.println( stringDate);
                System.out.println( e.getMessage() );
                return (null);
            }
            catch (StringIndexOutOfBoundsException e )
            {
                System.out.println( stringDate);
                System.out.println(e.getMessage());
            }
            stringDate = stringDate.substring(firstDivider +1, stringDate.length());
            int secondDivider = stringDate.indexOf("/");
            Integer month = null;
            Integer year = null;
            try {
                month = Integer.parseInt( stringDate.substring(0 , secondDivider) ) - 1;
                year = Integer.parseInt( stringDate.substring(stringDate.length()-4 , stringDate.length()) );
            } catch (Exception e)
            {
                System.out.println( stringDate);
                e.printStackTrace();
                return ( null );
            }
            cal.set(year, month, day);
            return (new java.sql.Date(cal.getTimeInMillis()));
        }
        return ( null );

    }
}
