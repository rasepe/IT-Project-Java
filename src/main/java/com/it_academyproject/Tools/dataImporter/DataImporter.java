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
    VicMyAppUserRepository myAppUserRepository;
    @Autowired
    VicCourseRepository courseRepository;
    @Autowired
    VicAbsenceRepository absenceRepository;
    @Autowired
    VicRoleRepository roleRepository;
    @Autowired
    VicItineraryRepository itineraryRepository;
    @Autowired
    VicExerciceRepository exerciceRepository;
    @Autowired
    VicStatusExerciceRepository statusExerciceRepository;
    @Autowired
    VicUserExerciceRepository userExerciceRepository;
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
                VicMyAppUser myAppUser = new VicMyAppUser();
                VicCourse vicCourse = new VicCourse();
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
                                Calendar cal = Calendar.getInstance();
                                cal.set(2019-Integer.parseInt(currentCell), 1, 1);
                                myAppUser.setBirthday ( new java.sql.Date(cal.getTimeInMillis()) );
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
                                    vicCourse.setBeginDate( null );
                                    vicCourse.setEndDate(null);
                                    vicCourse.setUserStudent(myAppUser);
                                }
                                else
                                {
                                    vicCourse.setBeginDate( stringToDate( currentCell) );
                                    vicCourse.setEndDate(null);
                                    vicCourse.setUserStudent(myAppUser);
                                }

                            }
                            else if ( j == 6)
                            {
                                if (! currentCell.equals(""))
                                {
                                    vicCourse.setEndDate( stringToDate(currentCell) );
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
                                    vicCourse.setUserStudent( myAppUser );
                                }
                                //course
                                List<VicCourse> vicCourseList = courseRepository.findByUserStudent( myAppUser );
                                //System.out.println( courseList );
                                //check if the student has a course
                                if (( vicCourseList == null ) || ( vicCourseList.size() == 0 ))
                                {
                                    vicCourse.setItinerary( itinerary );
                                    courseRepository.save(vicCourse);
                                }
                                if (! currentCell.equals(""))
                                {
                                    //set the absence days
                                    String[] array = currentCell.split(", ", -1);

                                    for (int k = 0; k < array.length; k++)
                                    {
                                        VicAbsence vicAbsence = new VicAbsence();
                                        vicAbsence.setDateMissing( stringToDate(array[k]));
                                        if ( vicAbsence.getDateMissing() != null )
                                        {
                                            vicAbsence.setUserStudent( myAppUser );
                                            //absence
                                            List<VicAbsence> vicAbsenceList = absenceRepository.findByUserStudentAndDateMissing( myAppUser , vicAbsence.getDateMissing());
                                            if (( vicAbsenceList == null ) ||  (vicAbsenceList.size() == 0 ))
                                            {
                                                //System.out.println(absence);
                                                absenceRepository.save(vicAbsence);
                                            }
                                            else
                                            {
                                                System.out.println(vicAbsenceList);
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

    public JSONObject importEjerciciosporalumno ()
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
            Map<Integer, List<String>> excelContent = excel.readJExcelContent(0);
            Map<Integer , Exercice> exerciceMap = new HashMap<>();
            //System.out.println( excelContent );

            // Get a set of the entries
            Set set = excelContent.entrySet();
            List<String> currentRow;

            for (Integer i : excelContent.keySet())
            {
                VicCourse vicCourse = new VicCourse();
                currentRow = excelContent.get(i);
                VicMyAppUser myAppUser = null;
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
                            List<Exercice> exercicesList = exerciceRepository.findAllByNameAndItinerary( currentCell , itineraryRepository.findOneById( 1 ) );
                            if ( (exercicesList == null ) || ( exercicesList.size() == 0) )
                            {
                                //exists do nothing
                                Integer exerciceKey = j - 3;
                                Exercice exercice = new Exercice();
                                exercice.setId( j );
                                exercice.setItinerary( itineraryRepository.findOneById( 1 ) );
                                exercice.setName( currentCell );
                                exerciceMap.put( j , exercice );
                                exerciceRepository.save( exercice );
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
                                List<VicMyAppUser> myAppUserList = myAppUserRepository.findByFirstNameAndLastName ( name , lastName );
                                if ((myAppUserList == null ) || ( myAppUserList.size() == 0 ))
                                {
                                    //user not found so we are going to look for contains
                                    //look by name
                                    List<VicMyAppUser> myAppUserList1 = (myAppUserRepository.findByFirstName( name ));
                                    if ((myAppUserList1 != null ) && ( myAppUserList1.size() != 0 ))
                                    {
                                        //check if it contains the last name
                                        for (int k = 0; k < myAppUserList1.size() ; k++)
                                        {
                                            System.out.println( myAppUserList1 );
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
                            Integer exerciceKey = j - 1;
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

            for (Integer i : excelContent.keySet()) {
                VicCourse vicCourse = new VicCourse();
                currentRow = excelContent.get(i);
                VicMyAppUser myAppUser = null;
                for (int j = 0; j < currentRow.size(); j++) {
                    String currentCell = currentRow.get(j);
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
