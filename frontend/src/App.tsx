import { useState } from "react";
import { Flex, Box } from "@chakra-ui/react";
import Sidebar from "./components/Sidebar";
import Content from "./components/Content";

export type ViewType = "shopping" | "expenses" | "stats";

function App() {
  const [view, setView] = useState<ViewType>("shopping");

  return (
    <Flex height="100vh" bg="gray.50">
      <Sidebar onSelect={setView} activeView={view} />
      <Box flex="1" p={6}>
        <Content view={view} />
      </Box>
    </Flex>
  );
}

export default App;
